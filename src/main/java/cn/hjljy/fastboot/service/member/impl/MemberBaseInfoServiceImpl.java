package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.autoconfig.exception.BusinessException;
import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.enums.SexEnum;
import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import cn.hjljy.fastboot.common.enums.member.MemberSourceEnum;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.LocalDateTimeUtil;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.mapper.member.MemberBaseInfoMapper;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoParam;
import cn.hjljy.fastboot.pojo.member.dto.MemberUpvDto;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import cn.hjljy.fastboot.service.member.IMemberLevelService;
import cn.hjljy.fastboot.service.member.IMemberUpvService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
@Service
@Slf4j
public class MemberBaseInfoServiceImpl extends BaseService<MemberBaseInfoMapper, MemberBaseInfo> implements IMemberBaseInfoService {

    @Autowired
    private IMemberLevelService memberLevelService;

    @Autowired
    private IMemberUpvService memberUpvService;


    @Override
    public IPage<MemberBaseInfoDto> getMemberBaseInfoPageList(MemberBaseInfoParam param) {
        IPage<MemberBaseInfoDto> page = param.createPage();
        return this.baseMapper.getMemberBaseInfoPageList(page, param.getOrgId(), param.getKeywords(), param.getLevelId());
    }

    @Override
    public Boolean addMember(MemberBaseInfoDto dto) {
        MemberBaseInfo info = new MemberBaseInfo();
        BeanUtils.copyProperties(dto, info);
        Long memberId = SnowFlakeUtil.createId();
        //判断是否重复注册
        checkMemberBaseInfo(dto.getOrgId(), dto.getMemberPhone(), dto.getMemberCard(), memberId);
        //设置默认等级以及当前会员成长值
        MemberLevel level = memberLevelService.getInitLevel(dto.getOrgId());
        info.setGrowthValue(level.getUpgradeGrowthValue());
        info.setLevelId(level.getLevelId());
        //未设置性别，默认为保密
        if (null == info.getMemberSex()) {
            info.setMemberSex(SexEnum.DEFAULT);
        }
        //未设置来源，默认为正常添加
        if (null == info.getSource()) {
            info.setSource(MemberSourceEnum.MANUALLY_ADD);
        }
        //处理新增的信息
        info.setMemberId(memberId);
        info.setBalance(BigDecimal.ZERO);
        info.setGenBalance(BigDecimal.ZERO);
        info.setMemberIntegral(0L);
        return this.save(info);
    }

    @Override
    public boolean delMember(Long memberId) {
        //会员不存在直接返回成功
        MemberBaseInfo baseInfo = this.getById(memberId);
        if (null == baseInfo) {
            return true;
        }
        //如果会员账上金额不为0 无法删除
        if (baseInfo.getBalance().equals(BigDecimal.ZERO) && baseInfo.getGenBalance().equals(BigDecimal.ZERO)) {
            baseInfo.setUpdateTime(LocalDateTime.now());
            baseInfo.setRemark(SecurityUtils.getUsername() + "手动删除该会员,操作时间：" + LocalDateTimeUtil.formatToString(LocalDateTime.now()));
            baseInfo.setStatus(1);
            return this.updateById(baseInfo);
        }
        throw new BusinessException(ResultCode.DEFAULT, "会员卡上金额不为0 无法删除");
    }

    @Override
    public Boolean editMember(MemberBaseInfoDto dto) {
        MemberBaseInfo baseInfo = this.memberExist(dto.getMemberId());
        //判断是否重复注册
        checkMemberBaseInfo(dto.getOrgId(), dto.getMemberPhone(), dto.getMemberCard(), dto.getMemberId());
        //处理编辑的信息 只能编辑部分信息，所以不采用copy属性的方式
        baseInfo.setMemberSex(dto.getMemberSex());
        baseInfo.setMemberCard(dto.getMemberCard());
        baseInfo.setMemberName(dto.getMemberName());
        baseInfo.setMemberPhone(dto.getMemberPhone());
        baseInfo.setMemberBirth(dto.getMemberBirth());
        baseInfo.setRemark(dto.getRemark());
        return this.updateById(baseInfo);
    }

    private void checkMemberBaseInfo(Long orgId, String memberPhone, String memberCard, Long memberId) {
        //判断机构会员手机号是否重复
        if (StringUtils.isNotEmpty(memberPhone)) {
            MemberBaseInfo memberBaseInfo = this.selectByPhoneAndOrgId(memberPhone, orgId);
            if (memberBaseInfo != null && !memberBaseInfo.getMemberId().equals(memberId)) {
                throw new BusinessException(ResultCode.MEMBER_EXIST, "手机号已被注册");
            }
        }
        //判断会员卡号是否重复
        if (StringUtils.isNotEmpty(memberCard)) {
            MemberBaseInfo baseInfo = this.selectByCardAndOrgId(memberCard, orgId);
            if (baseInfo != null && !baseInfo.getMemberId().equals(memberId)) {
                throw new BusinessException(ResultCode.MEMBER_EXIST, "会员卡号已被注册");
            }
        }
    }

    @Override
    public MemberBaseInfo selectByPhoneAndOrgId(String memberPhone, Long orgId) {
        MemberBaseInfo memberBaseInfo = new MemberBaseInfo();
        memberBaseInfo.setMemberPhone(memberPhone);
        memberBaseInfo.setOrgId(orgId);
        return this.selectOne(memberBaseInfo);
    }

    @Override
    public MemberBaseInfo selectByCardAndOrgId(String memberCard, Long orgId) {
        MemberBaseInfo memberBaseInfo = new MemberBaseInfo();
        memberBaseInfo.setMemberCard(memberCard);
        memberBaseInfo.setOrgId(orgId);
        return this.selectOne(memberBaseInfo);
    }

    @Override
    public MemberBaseInfo updateBalance(Long memberId, BigDecimal money, BigDecimal giftMoney, ConsumeTypeEnum consumeType) {
        // 更新会员金额
        MemberBaseInfo baseInfo = this.memberExist(memberId);
        log.info("会员：{}更新前，充值金额：{}，赠送金额：{}", baseInfo.getMemberName(), baseInfo.getBalance(), baseInfo.getGenBalance());
        if (ConsumeTypeEnum.isRecharge(consumeType)) {
            baseInfo.setBalance(baseInfo.getBalance().add(money));
            baseInfo.setGenBalance(baseInfo.getGenBalance().add(giftMoney));
        } else {
            baseInfo.setBalance(baseInfo.getBalance().subtract(money));
            baseInfo.setGenBalance(baseInfo.getGenBalance().subtract(giftMoney));
        }
        baseInfo.setUpdateTime(LocalDateTime.now());
        this.updateById(baseInfo);
        log.info("会员：{}更新后，充值金额：{}，赠送金额：{}", baseInfo.getMemberName(), baseInfo.getBalance(), baseInfo.getGenBalance());
        return baseInfo;
    }

    @Override
    public Integer updateGrowthValue(MemberBaseInfo baseInfo, BigDecimal money, ConsumeTypeEnum consumeType) {
        MemberUpvDto dto = memberUpvService.getByOrgId(baseInfo.getOrgId());
        int growthValue = 0;
        if (null != dto) {
            growthValue = money.divide(new BigDecimal(dto.getMoney()), RoundingMode.FLOOR).multiply(new BigDecimal(dto.getGrowthValue())).intValue();
            if (ConsumeTypeEnum.isRecharge(consumeType) && dto.getMemberRecharge()) {
                baseInfo.setGrowthValue(baseInfo.getGrowthValue() + growthValue);
            } else if (ConsumeTypeEnum.isNormalConsume(consumeType) && dto.getNormalConsume()) {
                baseInfo.setGrowthValue(baseInfo.getGrowthValue() + growthValue);
            } else if (ConsumeTypeEnum.isBalanceConsume(consumeType) && dto.getStoredConsume()) {
                baseInfo.setGrowthValue(baseInfo.getGrowthValue() + growthValue);
            } else if (ConsumeTypeEnum.isRefund(consumeType) && dto.getRefund()) {
                growthValue = growthValue * -1;
                baseInfo.setGrowthValue(baseInfo.getGrowthValue() + growthValue);
            }
        }
        log.info("会员ID:{},金额：{},消费类型：{}，获取的成长值:{}", baseInfo.getMemberId(), money, consumeType, growthValue);
        this.updateById(baseInfo);
        return growthValue;
    }

    @Override
    public void updateMemberLevel(MemberBaseInfo baseInfo) {
        MemberLevel level = memberLevelService.selectOrgLevelByGrowthValue(baseInfo.getGrowthValue(), baseInfo.getOrgId());
        if (null != level && !level.getLevelId().equals(baseInfo.getLevelId())) {
            baseInfo.setLevelId(level.getLevelId());
        }
    }

    @Override
    public MemberBaseInfo memberExist(Long memberId) {
        MemberBaseInfo baseInfo = this.getById(memberId);
        if (null == baseInfo) {
            throw new BusinessException(ResultCode.MEMBER_NOT_FOUND);
        }
        return baseInfo;
    }
}
