package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.mapper.member.MemberLevelMapper;
import cn.hjljy.fastboot.pojo.member.dto.MemberLevelDto;
import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.member.IMemberLevelService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
@Service
public class MemberLevelServiceImpl extends BaseService<MemberLevelMapper, MemberLevel> implements IMemberLevelService {

    @Override
    public MemberLevel selectOrgDefaultLevelId(Long orgId) {
        MemberLevel memberLevel = new MemberLevel();
        memberLevel.setMemberDefault(true);
        memberLevel.setOrgId(orgId);
        return this.selectOne(memberLevel);
    }

    @Override
    public List<MemberLevelDto> selectOrgMemberLevelList(Long orgId) {
        MemberLevel memberLevel = new MemberLevel();
        memberLevel.setOrgId(orgId);
        List<MemberLevel> list = this.selectList(memberLevel);
        List<MemberLevelDto> dtoList = new ArrayList<>();
        for (MemberLevel level : list) {
            MemberLevelDto dto = new MemberLevelDto();
            BeanUtil.copyProperties(level,dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public MemberLevel selectOrgLevelByLevelOrder(Long orgId, Integer levelOrder) {
        MemberLevel memberLevel = new MemberLevel();
        memberLevel.setOrgId(orgId);
        memberLevel.setLevelOrder(levelOrder);
        return this.selectOne(memberLevel);
    }

    @Override
    public MemberLevel selectByOrgIdAndLevelName(Long orgId, String levelName) {
        MemberLevel memberLevel = new MemberLevel();
        memberLevel.setOrgId(orgId);
        memberLevel.setLevelName(levelName);
        return this.selectOne(memberLevel);
    }

    @Override
    public Integer addLevel(MemberLevelDto dto) {
        MemberLevel level = new MemberLevel();
        BeanUtil.copyProperties(dto, level);
        MemberLevel memberLevel = this.selectOrgLevelByLevelOrder(dto.getOrgId(), dto.getLevelOrder());
        if (memberLevel != null) {
            throw new BusinessException(ResultCode.MEMBER_LEVEL_EXIST);
        }
        memberLevel = this.selectByOrgIdAndLevelName(dto.getOrgId(), dto.getLevelName());
        if(memberLevel !=null){
            throw new BusinessException(ResultCode.MEMBER_LEVEL_EXIST); 
        }
        if(dto.getMemberDefault()){
            MemberLevel defaultLevel = this.selectOrgDefaultLevelId(dto.getOrgId());
            if(defaultLevel!=null){
                defaultLevel.setMemberDefault(false);
                defaultLevel.setUpdateTime(LocalDateTime.now());
                defaultLevel.setUpdateUser(SecurityUtils.getUserId());
                this.updateById(defaultLevel);
            }
        }
        level.setCreateTime(LocalDateTime.now());
        level.setCreateUser(SecurityUtils.getUserId());
        level.setLevelId(SnowFlakeUtil.createID());
        return this.baseMapper.insert(level);
    }
}
