package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.mapper.member.MemberLevelMapper;
import cn.hjljy.fastboot.pojo.member.dto.MemberLevelDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberLevelParam;
import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.member.IMemberLevelService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            BeanUtil.copyProperties(level, dto);
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
    public MemberLevel selectOrgMaxMemberLevel(Long orgId) {
        QueryWrapper<MemberLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(level_order) as level_order", "level_id", "level_name", "upgrade_growth_value");
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer addLevel(MemberLevelDto dto) {
        Long userId = SecurityUtils.getUserId();
        MemberLevel level = new MemberLevel();
        BeanUtil.copyProperties(dto, level);
        MemberLevel maxMemberLevel = this.selectOrgMaxMemberLevel(dto.getOrgId());
        level.setLevelOrder(maxMemberLevel.getLevelOrder() + 1);
        //1 判断等级是否存在
        MemberLevel memberLevel = this.selectByOrgIdAndLevelName(dto.getOrgId(), dto.getLevelName());
        if (memberLevel != null) {
            throw new BusinessException(ResultCode.MEMBER_LEVEL_EXIST, "会员等级名称重复");
        }
        //2 判断默认等级是否已设置
        if (dto.getMemberDefault()) {
            this.resetDefaultLevel(userId, dto.getOrgId());
        }
        //3 保存新的会员等级
        if(level.getLevelOrder()==1){
            //第一会员等级所需成长值默认为0
            level.setUpgradeGrowthValue(0);
        }
        level.setCreateTime(LocalDateTime.now());
        level.setUpdateTime(LocalDateTime.now());
        level.setUpdateUser(userId);
        level.setCreateUser(userId);
        level.setLevelId(SnowFlakeUtil.createId());
        return this.baseMapper.insert(level);
    }

    @Override
    public Integer editLevel(MemberLevelDto dto) {
        Long userId = SecurityUtils.getUserId();
        Long orgId = dto.getOrgId();
        MemberLevel level = new MemberLevel();
        BeanUtil.copyProperties(dto, level);
        level.setUpdateTime(LocalDateTime.now());
        level.setUpdateUser(userId);
        //1 判断是否需要重置默认会员等级
        if (dto.getMemberDefault()) {
            this.resetDefaultLevel(userId, orgId);
        }
        //2 判断成长值是否合法
        int beforeOrder = dto.getLevelOrder() - 1;
        int beforeUPV = 0;
        int afterOrder = dto.getLevelOrder() + 1;
        int afterUPV = 1000000000;
        if (beforeOrder > 0) {
            MemberLevel beforeLevel = this.selectOrgLevelByLevelOrder(orgId, beforeOrder);
            beforeUPV = beforeLevel.getUpgradeGrowthValue();
        }
        MemberLevel afterLevel = this.selectOrgLevelByLevelOrder(orgId, afterOrder);
        if (null != afterLevel) {
            afterUPV = afterLevel.getUpgradeGrowthValue();
        }
        if (dto.getUpgradeGrowthValue() <= beforeUPV || afterUPV <= dto.getUpgradeGrowthValue()) {
            throw new BusinessException(ResultCode.PARAMETERS_EXCEPTION, "会员所需成长值范围需要在" + beforeUPV + "-" + afterUPV + "之间");
        }
        //3 判断名称是否重复
        MemberLevel memberLevel = this.selectByOrgIdAndLevelName(dto.getOrgId(), dto.getLevelName());
        if (memberLevel != null) {
            throw new BusinessException(ResultCode.MEMBER_LEVEL_EXIST, "会员等级名称重复");
        }
        return this.baseMapper.updateById(level);
    }

    @Override
    public Boolean delLevel(MemberLevelParam param) {
        //1 删除本条数据
        MemberLevel level = this.getById(param.getLevelId());
        if (null == level) {
            throw new BusinessException(ResultCode.DEFAULT, "会员等级不存在，无法删除");
        }
        this.removeById(param.getLevelId());
        log.info("删除机构id:{}的会员等级:{}", param.getOrgId(), level.getLevelName());
        //2 将大于本等级的会员等级减1
        UpdateWrapper<MemberLevel> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(MemberLevel::getOrgId, param.getOrgId())
                .ge(MemberLevel::getLevelOrder, level.getLevelOrder())
                .setSql("level_order = level_order-1");
        return this.update(wrapper);
    }

    @Override
    public void resetDefaultLevel(Long userId, Long orgId) {
        MemberLevel defaultLevel = this.selectOrgDefaultLevelId(orgId);
        if (defaultLevel != null) {
            defaultLevel.setMemberDefault(false);
            defaultLevel.setUpdateTime(LocalDateTime.now());
            defaultLevel.setUpdateUser(userId);
            this.updateById(defaultLevel);
            log.info("取消{}作为新会员默认等级", defaultLevel.getLevelName());
        }
    }
}
