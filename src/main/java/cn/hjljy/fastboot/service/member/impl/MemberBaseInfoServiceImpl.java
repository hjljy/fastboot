package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.autoconfig.exception.BusinessException;
import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.enums.SexEnum;
import cn.hjljy.fastboot.common.enums.member.MemberSourceEnum;
import cn.hjljy.fastboot.common.enums.member.OrderTypeEnum;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.LocalDateTimeUtil;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.mapper.member.MemberBaseInfoMapper;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoParam;
import cn.hjljy.fastboot.pojo.member.dto.MemberDto;
import cn.hjljy.fastboot.pojo.member.dto.RechargeParam;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import cn.hjljy.fastboot.pojo.member.po.MemberOrderInfo;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import cn.hjljy.fastboot.service.member.IMemberLevelService;
import cn.hjljy.fastboot.service.member.IMemberMoneyRecordService;
import cn.hjljy.fastboot.service.member.IMemberOrderInfoService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private IMemberOrderInfoService orderInfoService;

    @Autowired
    private IMemberMoneyRecordService moneyRecordService;

    @Override
    public IPage<MemberBaseInfoDto> getMemberBaseInfoPageList(MemberBaseInfoParam param) {
        IPage<MemberBaseInfoDto> page = param.createPage();
        return this.baseMapper.getMemberBaseInfoPageList(page, param.getOrgId(), param.getKeywords(), param.getLevelId());
    }

    @Override
    public int addMember(MemberBaseInfoDto dto) {
        MemberBaseInfo info = new MemberBaseInfo();
        BeanUtil.copyProperties(dto, info);
        Long memberId = SnowFlakeUtil.createId();
        //判断是否重复注册
        checkMemberBaseInfo(dto.getOrgId(), dto.getMemberPhone(), dto.getMemberCard(), memberId);
        //设置默认等级以及当前会员成长值
        MemberLevel level = memberLevelService.selectOrgDefaultLevelId(dto.getOrgId());
        if (null == level) {
            info.setGrowthValue(0);
            MemberLevel order = memberLevelService.selectOrgLevelByLevelOrder(dto.getOrgId(), 1);
            if (null != order) {
                info.setGrowthValue(order.getUpgradeGrowthValue());
                info.setLevelId(order.getLevelId());
            } else {
                log.warn("机构id:{} 未设置新会员默认等级并且最低会员等级所需成长值大于0", dto.getOrgId());
                info.setGrowthValue(0);
                info.setLevelId(-1L);
            }
        } else {
            info.setGrowthValue(level.getUpgradeGrowthValue());
            info.setLevelId(level.getLevelId());
        }
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
        return this.baseMapper.insert(info);
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
            baseInfo.setUpdateUser(SecurityUtils.getUserId());
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
    public MemberDto getMemberDto(Long memberId) {
        MemberBaseInfo baseInfo = this.memberExist(memberId);
        return null;
    }

    @Override
    public MemberDto memberRecharge(RechargeParam param) {
        MemberBaseInfo baseInfo = this.memberExist(param.getMemberId());
        MemberOrderInfo orderInfo = orderInfoService.createOrder(baseInfo.getMemberId(), param.getOrderSource(), param.getPayType(), param.getMoney(), OrderTypeEnum.NORMAL);
        orderInfoService.success(orderInfo.getOrderNum(),param.getMoney(),param.getPayType(),"消费类型");
        return null;
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
