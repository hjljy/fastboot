package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysRoleDto;
import cn.hjljy.fastboot.pojo.sys.po.SysRole;
import cn.hjljy.fastboot.mapper.sys.SysRoleMapper;
import cn.hjljy.fastboot.service.sys.ISysRoleService;
import cn.hjljy.fastboot.service.BaseService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-10
 */
@Service
public class SysRoleServiceImpl extends BaseService<SysRoleMapper, SysRole> implements ISysRoleService {


    @Override
    public List<SysRole> getUserRoleInfo(Long userId) {
        List<SysRole> roleDtos = baseMapper.selectUserRoleInfoByUserId(userId);
        return roleDtos;
    }

    @Override
    public List<SysRoleDto> getUserRoleInfos(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        return baseMapper.selectUserRoleInfoByUserIds(userIds);
    }

    @Override
    public List<SysRoleDto> list(BaseDto param) {
        List<SysRoleDto> roleDtoList = new ArrayList<>();
        SysRole role = new SysRole();
        role.setOrgId(param.getOrgId());
        List<SysRole> roles = selectList(role);
        for (SysRole sysRole : roles) {
            SysRoleDto roleDto =new SysRoleDto();
            BeanUtil.copyProperties(sysRole,roleDto);
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }
}
