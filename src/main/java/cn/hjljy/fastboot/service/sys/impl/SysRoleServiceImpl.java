package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.enums.sys.SysUserTypeEnum;
import cn.hjljy.fastboot.pojo.sys.dto.SysRoleDto;
import cn.hjljy.fastboot.pojo.sys.po.SysRole;
import cn.hjljy.fastboot.mapper.sys.SysRoleMapper;
import cn.hjljy.fastboot.service.sys.ISysRoleService;
import cn.hjljy.fastboot.service.BaseService;
import cn.hutool.core.bean.BeanUtil;
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
    public List<SysRole> getUserRoleInfo(Long userId, String userType, Long orgId) {
        List<SysRole> roleDos = new ArrayList<>();
        //如果是普通账号 查询角色权限信息 如果是管理员账号（超级管理员，机构管理员 直接返回空数组）
        if (SysUserTypeEnum.NORMAL.name().equals(userType)) {
            roleDos = baseMapper.selectUserRoleInfoByUserId(userId, orgId);
        }
        return roleDos;
    }

    @Override
    public List<SysRoleDto> getUserRoleInfos(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        return baseMapper.selectUserRoleInfoByUserIds(userIds);
    }

    @Override
    public List<SysRoleDto> list(SysRoleDto param) {
        List<SysRoleDto> roleDtoList = new ArrayList<>();
        SysRole role = new SysRole();
        Long orgId = param.getOrgId();
        // 如果没有传机构ID，默认查询本账号所在机构角色信息
        if (null == orgId) {
            orgId = SecurityUtils.getUserInfo().getOrgId();
        }
        role.setOrgId(orgId);
        List<SysRole> roles = selectList(role);
        for (SysRole sysRole : roles) {
            SysRoleDto roleDto = new SysRoleDto();
            BeanUtil.copyProperties(sysRole, roleDto);
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }
}
