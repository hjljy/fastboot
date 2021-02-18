package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.enums.SexEnum;
import cn.hjljy.fastboot.common.enums.member.MemberSourceEnum;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.LocalDateTimeUtil;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.mapper.member.MemberBaseInfoMapper;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import cn.hjljy.fastboot.service.member.IMemberLevelService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public IPage<MemberBaseInfoDto> getMemberBaseInfoPageList(Long orgId, String keywords, Long levelId, Integer pageNo, Integer pageNum) {
        IPage<MemberBaseInfoDto> page = new Page<>();
        page.setPages(pageNo);
        page.setSize(pageNum);
        if (levelId == null) {
            levelId = 0L;
        }
        return this.baseMapper.getMemberBaseInfoPageList(page, orgId, keywords, levelId);
    }

    @Override
    public int addMember(MemberBaseInfoDto dto) {
        MemberBaseInfo info = new MemberBaseInfo();
        BeanUtil.copyProperties(dto, info);
        Long memberId = SnowFlakeUtil.createID();
        //判断是否重复注册
        checkMemberBaseInfo(dto.getOrgId(), dto.getMemberPhone(), dto.getMemberCard(), memberId);
        //处理默认信息
        MemberLevel level = memberLevelService.selectOrgDefaultLevelId(dto.getOrgId());
        if (level == null) {
            info.setGrowthValue(0);
            MemberLevel order = memberLevelService.selectOrgLevelByLevelOrder(dto.getOrgId(), 1);
            if (order != null && order.getUpgradeGrowthValue() != 0) {
                info.setLevelId(order.getLevelId());
            } else {
                log.warn("机构id:{} 未设置新会员默认等级并且最低会员等级所需成长值大于0", dto.getOrgId());
                info.setLevelId(0L);
            }
        } else {
            info.setGrowthValue(level.getUpgradeGrowthValue());
            info.setLevelId(level.getLevelId());
        }
        //未设置性别，默认为保密
        if (info.getMemberSex() == null) {
            info.setMemberSex(SexEnum.DEFAULT.getCode());
        }
        //未设置来源，默认为正常添加
        if (StringUtils.isEmpty(dto.getSource())) {
            info.setSource(MemberSourceEnum.NORMAL.name());
        }
        //未设置会员卡号，默认随机生成会员卡号
        if (StringUtils.isEmpty(dto.getMemberCard())) {
            info.setMemberCard(SnowFlakeUtil.createStringID());
        }
        //处理新增的信息
        info.setMemberId(memberId);
        info.setBalance(0L);
        info.setGenBalance(0L);
        info.setLevelId(dto.getLevelId());
        info.setMemberIntegral(0L);
        info.setCreateTime(LocalDateTime.now());
        info.setUpdateTime(LocalDateTime.now());
        info.setUpdateUser(SecurityUtils.getUserId());
        info.setCreateUser(SecurityUtils.getUserId());
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
        if (baseInfo.getBalance().equals(0L) && baseInfo.getGenBalance().equals(0L)) {
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
        MemberBaseInfo baseInfo = this.getById(dto.getMemberId());
        if (null == baseInfo) {
            throw new BusinessException(ResultCode.MEMBER_NOT_FOUND);
        }
        //判断是否重复注册
        checkMemberBaseInfo(dto.getOrgId(), dto.getMemberPhone(), dto.getMemberCard(), dto.getMemberId());
        //处理编辑的信息
        baseInfo.setMemberSex(dto.getMemberSex());
        baseInfo.setMemberCard(dto.getMemberCard());
        baseInfo.setMemberName(dto.getMemberName());
        baseInfo.setMemberPhone(dto.getMemberPhone());
        baseInfo.setMemberBirth(dto.getMemberBirth());
        baseInfo.setRemark(dto.getRemark());
        baseInfo.setUpdateTime(LocalDateTime.now());
        baseInfo.setUpdateUser(SecurityUtils.getUserId());
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
}
