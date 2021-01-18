package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.mapper.sys.SysUserMapper;
import cn.hjljy.fastboot.pojo.sys.dto.SysMenuDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysRoleDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserParam;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hjljy.fastboot.pojo.sys.po.SysRole;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.pojo.sys.po.SysUserRole;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.sys.ISysMenuService;
import cn.hjljy.fastboot.service.sys.ISysRoleService;
import cn.hjljy.fastboot.service.sys.ISysUserRoleService;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.util.deparser.UpdateDeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-09
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseService<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    ISysRoleService roleService;

    @Autowired
    ISysUserRoleService userRoleService;

    @Autowired
    ISysMenuService menuService;

    @Override
    public SysUser selectByUserName(String username) {
        SysUser po = new SysUser();
        po.setUserName(username);
        return selectOne(po);
    }

    @Override
    public IPage<SysUserDto> getSysUserInfoPage(SysUserParam param) {
        Page<SysUserDto> page = param.createPage();
        //查询用户基础信息
        List<SysUserDto> infoPage = this.baseMapper.getSysUserInfoPage(page, param.getOrgId(), param.getKeywords(), param.getRoleId());
        if (!CollectionUtils.isEmpty(infoPage)) {
            List<Long> userIds = infoPage.stream().map(SysUserDto::getId).collect(Collectors.toList());
            //查询用户角色信息
            List<SysRoleDto> roles = roleService.getUserRoleInfos(userIds);
            infoPage.forEach(n -> {
                List<SysRoleDto> list = roles.stream().filter(m -> m.getUserId().equals(n.getId())).collect(Collectors.toList());
                n.setRoles(list);
            });
        }
        page.setRecords(infoPage);
        return page;
    }

    @Override
    public SysUserDto getUserDetailInfoByUserId(Long userId) {
        SysUserDto userDto = new SysUserDto();
        //查询用户基础信息
        SysUser sysUser = this.baseMapper.selectById(userId);
        if (sysUser == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND_OR_ENABLE);
        }
        BeanUtil.copyProperties(sysUser, userDto, "password");
        //查询用户角色信息
        List<SysRole> roleInfo = roleService.getUserRoleInfo(userId);
        List<SysRoleDto> roles = new ArrayList<>();
        for (SysRole role : roleInfo) {
            SysRoleDto roleDto = new SysRoleDto();
            BeanUtil.copyProperties(role, roleDto);
            roles.add(roleDto);
        }
        userDto.setRoles(roles);
        //查询角色菜单权限信息
        List<SysMenu> menuList = menuService.getUserMenuListInfo(userId);
        List<SysMenuDto> menus = new ArrayList<>();
        for (SysMenu menu : menuList) {
            SysMenuDto roleDto = new SysMenuDto();
            BeanUtil.copyProperties(menu, roleDto);
            menus.add(roleDto);
        }
        userDto.setMenus(menus);
        return userDto;
    }

    @Override
    public void addSysUserInfo(SysUserDto dto) {
        // 1 判断用户账号是否已存在
        SysUser userName = this.selectByUserName(dto.getUserName());
        if (null != userName) {
            throw new BusinessException(ResultCode.USER_EXIST);
        }
        SysUser user = new SysUser();
        BeanUtil.copyProperties(dto, user);
        Long userId = SnowFlakeUtil.createID();
        String password = SecurityUtils.encryptPassword(user.getPassword());
        user.setPassword(password);
        user.setId(userId);
        //2 保存用户信息
        this.baseMapper.insert(user);
        List<SysRoleDto> roles = dto.getRoles();
        //3 保存用户角色信息
        this.saveUserRole(roles,userId);
    }



    @Override
    public void updateSysUserInfo(SysUserDto param) {
        // 1 判断用户是否存在
        SysUser user = this.getById(param.getId());
        if (null == user) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND_OR_ENABLE);
        }
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(param, sysUser);
        // 2 更新时，不允许更新用户账号,账号置为null
        sysUser.setUserName(null);
        // 3 更新时，不允许更新用户密码,密码置为null
        sysUser.setPassword(null);
        sysUser.setUpdateTime(LocalDateTime.now());
        // 4 更新用户基础信息
        this.updateById(sysUser);
        // 5 更新用户权限信息
        UpdateWrapper<SysUserRole> wrapper =new UpdateWrapper<>();
        wrapper.lambda().eq(SysUserRole::getUserId,param.getId());
        userRoleService.remove(wrapper);
        List<SysRoleDto> roles = param.getRoles();
        this.saveUserRole(roles,param.getId());

    }

    /**
     * 保存用户角色信息
     * @param roles 角色信息
     * @param userId 用户ID
     */
    private void saveUserRole(List<SysRoleDto> roles, Long userId) {
        if (CollectionUtil.isNotEmpty(roles)) {
            List<SysUserRole> userRoles = new ArrayList<>();
            for (SysRoleDto role : roles) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(role.getId());
                userRole.setUserId(userId);
                userRole.setStatus(0);
                userRoles.add(userRole);
            }
            userRoleService.saveBatch(userRoles);
        }
    }
}
