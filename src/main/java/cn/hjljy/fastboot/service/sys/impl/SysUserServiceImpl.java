package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.constant.RedisPrefixConstant;
import cn.hjljy.fastboot.common.enums.sys.SysUserTypeEnum;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.mapper.sys.SysUserMapper;
import cn.hjljy.fastboot.pojo.sys.dto.*;
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
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    @Autowired
    RedissonClient redissonClient;

    @Override
    public SysUser selectByUserName(String username) {
        SysUser po = new SysUser();
        po.setUserName(username);
        return this.selectOne(po);
    }

    @Override
    public IPage<SysUserDto> getSysUserInfoPage(SysUserParam param) {
        Page<SysUserDto> page = param.createPage();
        //1 查询用户基础信息
        List<SysUserDto> infoPage = this.baseMapper.getSysUserInfoPage(page, param.getOrgId(), param.getKeywords(), param.getRoleId());
        if (!CollectionUtils.isEmpty(infoPage)) {
            List<Long> userIds = infoPage.stream().map(SysUserDto::getId).collect(Collectors.toList());
            //2 查询用户角色信息
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
        //1 查询用户基础信息
        SysUser sysUser = this.userIfExist(userId);
        BeanUtil.copyProperties(sysUser, userDto, "password");
        //2 查询用户角色信息
        List<SysRole> roleInfo = roleService.getUserRoleInfo(userId, sysUser.getUserType(), sysUser.getOrgId());
        List<SysRoleDto> roles = new ArrayList<>();
        for (SysRole role : roleInfo) {
            SysRoleDto roleDto = new SysRoleDto();
            BeanUtil.copyProperties(role, roleDto);
            roles.add(roleDto);
        }
        userDto.setRoles(roles);
        //3 查询角色菜单权限信息
        List<SysMenu> menuList = menuService.getUserMenuListInfo(userId, sysUser.getUserType(), sysUser.getOrgId());
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
        user.setUserType(SysUserTypeEnum.NORMAL.name());
        Long userId = SnowFlakeUtil.createId();
        String password = SecurityUtils.encryptPassword(user.getPassword());
        user.setEnable(0);
        user.setPassword(password);
        user.setId(userId);
        //3 保存用户信息
        this.baseMapper.insert(user);
        //4 保存用户角色信息
        this.saveUserRole(dto.getRoleIds(), userId);
    }

    @Override
    public void updateSysUserInfo(SysUserDto param) {
        boolean b = SecurityUtils.getUserId().equals(param.getId());
        // 1 判断用户是否存在
        SysUser sysUser = userIfExist(param.getId());
        BeanUtil.copyProperties(param, sysUser);
        // 2 更新时，不允许更新用户账号类型,类型置为null
        sysUser.setUserType(SysUserTypeEnum.NORMAL.name());
        // 3 更新时，不允许更新用户账号,账号置为null
        sysUser.setUserName(null);
        // 4 更新时，不允许更新用户密码,密码置为null
        sysUser.setPassword(null);
        // 5 判断是否是当前用户，当前用户无法更新机构信息
        if (b) {
            sysUser.setOrgId(null);
        }
        // 6 更新用户基础信息
        this.updateById(sysUser);
        // 7 判断是否是当前用户，当前用户无法更新权限信息
        if (!b) {
            UpdateWrapper<SysUserRole> wrapper = new UpdateWrapper<>();
            wrapper.lambda().eq(SysUserRole::getUserId, param.getId());
            userRoleService.remove(wrapper);
            this.saveUserRole(param.getRoleIds(), param.getId());
        }
    }

    @Override
    public void disableSysUser(SysUserParam param) {
        // 1 判断是否是当前用户，当前用户不支持禁用操作
        if (SecurityUtils.getUserId().equals(param.getUserId())) {
            throw new BusinessException(ResultCode.DEFAULT, "无法禁用当前登录用户");
        }
        // 2 判断用户是否存在
        SysUser user = this.userIfExist(param.getUserId());
        user.setEnable(param.getEnable());
        this.updateById(user);
    }

    @Override
    public void removeSysUser(Long userId) {
        // 1 判断是否是当前用户，当前用户不支持删除操作
        if (SecurityUtils.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.DEFAULT, "无法删除当前登录用户");
        }
        // 2 直接逻辑删除用户
        this.removeById(userId);
    }

    @Override
    public SysUser userIfExist(Long userId) throws BusinessException {
        SysUser user = this.getById(userId);
        if (null == user) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public void resetUserPassword(Long userId) {
        SysUser sysUser = userIfExist(userId);
        sysUser.setPassword(SecurityUtils.encryptPassword("123456"));
        this.updateUserPassword(sysUser);


    }

    @Override
    public void editUserPassword(PasswordParam param) {
        if (!param.getUserId().equals(SecurityUtils.getUserId())) {
            throw new BusinessException(ResultCode.USER_NOT_MATCH);
        }
        if (!param.getNewPassword().equals(param.getRPassword())) {
            throw new BusinessException(ResultCode.USER_NOT_MATCH, "新密码和重复密码不匹配，请检查后重试");
        }
        SysUser sysUser = userIfExist(param.getUserId());
        if (!SecurityUtils.matchesPassword(param.getOldPassword(), sysUser.getPassword())) {
            throw new BusinessException(ResultCode.USER_NOT_MATCH, "原始密码不正确，请检查后重试");
        }
        sysUser.setPassword(SecurityUtils.encryptPassword(param.getNewPassword()));
        this.updateUserPassword(sysUser);
    }

    @Override
    public void updateUserPassword(SysUser sysUser) {
        baseMapper.updateById(sysUser);
        //重置密码后需要重置登录的token
        redissonClient.getMap(RedisPrefixConstant.LOGIN_USER_TOKEN + sysUser.getId()).delete();
    }

    @Override
    public void bindPhone(String phone) {
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = userIfExist(userId);
        if (phone.equals(sysUser.getPhone())) {
            throw new BusinessException(ResultCode.DEFAULT, "新号码与旧号码不能相同");
        }
        List<SysUser> users = this.selectByPhone(phone);
        long count = users.stream().filter(n -> !n.getId().equals(userId)).count();
        if (count > 0) {
            throw new BusinessException(ResultCode.DEFAULT, "一个手机号码只能绑定一个账号");
        }
        sysUser.setPhone(phone);
        baseMapper.updateById(sysUser);
    }

    @Override
    public List<SysUser> selectByPhone(String phone) {
        SysUser po = new SysUser();
        po.setPhone(phone);
        return selectList(po);
    }

    /**
     * 保存用户角色信息
     *
     * @param roles  角色信息
     * @param userId 用户ID
     */
    private void saveUserRole(List<Integer> roles, Long userId) {
        if (CollectionUtil.isNotEmpty(roles)) {
            List<SysUserRole> userRoles = new ArrayList<>();
            for (Integer roleId : roles) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRole.setStatus(0);
                userRoles.add(userRole);
            }
            userRoleService.saveBatch(userRoles);
        }
    }
}
