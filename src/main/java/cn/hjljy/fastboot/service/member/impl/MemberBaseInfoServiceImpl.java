package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.enums.SexEnum;
import cn.hjljy.fastboot.common.enums.SourceEnum;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import cn.hjljy.fastboot.mapper.member.MemberBaseInfoMapper;
import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.member.IMemberLevelService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
@Service
public class MemberBaseInfoServiceImpl extends BaseService<MemberBaseInfoMapper, MemberBaseInfo> implements IMemberBaseInfoService {
    @Autowired
    private IMemberLevelService memberLevelService;

    @Override
    public IPage<MemberBaseInfoDto> getMemberBaseInfoPageList(String orgId, String keywords, Long levelId, Integer pageNo, Integer pageNum) {
        IPage<MemberBaseInfoDto> page = new Page<>();
        page.setPages(pageNo);
        page.setSize(pageNum);
        if(levelId==null){
            levelId=0l;
        }
        return this.baseMapper.getMemberBaseInfoPageList(page,orgId,keywords,levelId);
    }

    @Override
    public int addMember(MemberBaseInfoDto dto) {
        MemberBaseInfo info =new MemberBaseInfo();
        BeanUtil.copyProperties(dto,info);
        //判断是否存在
        MemberBaseInfo memberBaseInfo=this.selectByPhoneAndOrgId(dto.getMemberPhone(),dto.getOrgId());
        if(memberBaseInfo!=null){
            throw new BusinessException(ResultCode.MEMBER_EXIST);
        }
        //处理默认信息
        MemberLevel level = memberLevelService.selectOrgDefaultLevelId(dto.getOrgId());
        if(level==null){
            dto.setLevelId(-1L);
        }else {
            dto.setLevelId(level.getLevelId());
        }
        if(info.getMemberSex()==null){
            dto.setMemberSex(SexEnum.DEFAULT.getCode());
        }
        if(StringUtils.isEmpty(dto.getSource())){
            dto.setSource(SourceEnum.NORMAL.name());
        }
        if(StringUtils.isEmpty(dto.getMemberCard())){
            dto.setMemberCard(SnowFlakeUtil.createCardID("MN"));
        }
        info.setMemberId(SnowFlakeUtil.createID());
        info.setMemberSex(dto.getMemberSex());
        info.setBalance(0L);
        info.setGenBalance(0L);
        info.setGrowthValue(0);
        info.setLevelId(dto.getLevelId());
        info.setMemberIntegral(0L);
        info.setRemark(dto.getRemark());
        info.setSource(dto.getSource());
        info.setCreateTime(LocalDateTime.now());
        info.setCreateUser(SecurityUtils.getUserInfo().getUserId());
        return this.baseMapper.insert(info);
    }

    @Override
    public MemberBaseInfo selectByPhoneAndOrgId(String memberPhone, Long orgId) {
        MemberBaseInfo memberBaseInfo =new MemberBaseInfo();
        memberBaseInfo.setMemberPhone(memberPhone);
        memberBaseInfo.setOrgId(orgId);
        return this.selectOne(memberBaseInfo);
    }
}
