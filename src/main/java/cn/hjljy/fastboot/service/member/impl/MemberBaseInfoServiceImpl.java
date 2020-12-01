package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.common.enums.SexEnum;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import cn.hjljy.fastboot.mapper.member.MemberBaseInfoMapper;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import cn.hjljy.fastboot.service.BaseService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public Boolean addMember(MemberBaseInfoDto dto) {
        MemberBaseInfo info =new MemberBaseInfo();
        BeanUtil.copyProperties(dto,info);
        MemberBaseInfo memberBaseInfo=this.selectByPhoneAndOrgId(dto.getMemberPhone(),dto.getOrgId());
        if(memberBaseInfo!=null){
            throw new BusinessException(ResultCode.MEMBER_EXIST);
        }
        info.setMemberId(SnowFlakeUtil.createID());
        if(info.getMemberSex()==null){
            info.setMemberSex(SexEnum.DEFAULT.getCode());
        }
        info.setCreateTime(LocalDateTime.now());
        this.baseMapper.insert(info);
        return null;
    }

    @Override
    public MemberBaseInfo selectByPhoneAndOrgId(String memberPhone, Long orgId) {
        MemberBaseInfo memberBaseInfo =new MemberBaseInfo();
        memberBaseInfo.setMemberPhone(memberPhone);
        memberBaseInfo.setOrgId(orgId);
        return this.selectOne(memberBaseInfo);
    }
}
