package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.common.enums.StatusEnum;
import cn.hjljy.fastboot.common.enums.sys.SysOrgStateEnum;
import cn.hjljy.fastboot.common.enums.sys.SysUserTypeEnum;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hjljy.fastboot.pojo.sys.po.SysOrg;
import cn.hjljy.fastboot.pojo.sys.po.SysRole;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.sys.ISysMenuService;
import cn.hjljy.fastboot.service.sys.ISysOrgService;
import cn.hjljy.fastboot.service.sys.ISysRoleService;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 海加尔金鹰
 * @apiNote 用户具体验证类
 * @since 2020/9/11
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    ISysUserService userService;
    @Autowired
    ISysRoleService roleService;
    @Autowired
    ISysMenuService menuService;
    @Autowired
    ISysOrgService orgService;

    /**
     * 这里根据传进来的用户账号进行用户信息的构建
     * 通常的做法是
     * 1 根据username查询数据库对应的用户信息
     * 2 根据用户信息查询出用户权限信息  例如菜单添加权限  sys:menu:add
     * 3 根据用户账号，密码，权限构建对应的UserDetails对象返回
     * 这里实际上是没有进行用户认证功能的，真正的验证是在UsernamePasswordAuthenticationFilter对象当中
     * UsernamePasswordAuthenticationFilter对象会自动根据前端传入的账号信息和UserDetails对象对比进行账号的验证
     * 通常情况下，已经满足常见的使用常见，不过如果有特殊需求，需要使用自己实现的具体认证方式，可以继承UsernamePasswordAuthenticationFilter对象
     * 重写attemptAuthentication 方法和successfulAuthentication方法
     * 最后在WebSecurityConfiguration里面添加自己的过滤器即可
     *
     * @param username 用户账号
     * @return UserInfo
     * @throws UsernameNotFoundException 用户账户密码不正确异常
     */
    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        //1 获取用户信息
        SysUser userInfo = userService.selectByUserName(username);
        //2 校验用户信息
        checkUserInfo(userInfo, username);
        //3 判断用户所属机构是否过期,启用
        SysOrg sysOrg = orgService.orgIfExist(userInfo.getOrgId());
        checkOrgInfo(sysOrg);
        //4 根据用户类型获取用户角色权限信息
        List<SysRole> roleInfo = roleService.getUserRoleInfo(userInfo.getId(), userInfo.getUserType(), userInfo.getOrgId());
        List<SysMenu> menuListInfo = menuService.getUserMenuListInfo(userInfo.getId(), userInfo.getUserType(), userInfo.getOrgId());
        String[] perms = menuListInfo.stream().map(SysMenu::getPerms).toArray(String[]::new);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(perms);
        //5 构建角色账号信息
        UserInfo user = new UserInfo(username, userInfo.getPassword(), authorityList);
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());
        user.setUserType(userInfo.getUserType());
        user.setNickName(userInfo.getNickName());
        user.setRoleDtos(roleInfo);
        user.setUserId(userInfo.getId());
        user.setAvatarUrl(userInfo.getAvatarUrl());
        user.setOrgId(userInfo.getOrgId());
        return user;
    }

    private void checkOrgInfo(SysOrg sysOrg) {
        if(SysOrgStateEnum.DISABLE.name().equals(sysOrg.getOrgState())){
            throw new BusinessException(ResultCode.ORG_DISABLE);
        }else if(SysOrgStateEnum.EXPIRE.name().equals(sysOrg.getOrgState())){
            throw new BusinessException(ResultCode.ORG_EXPIRED);
        }
    }

    private void checkUserInfo(SysUser userInfo, String username) {
        if (null == userInfo) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND, username + ResultCode.USER_NOT_FOUND.getMsg());
        } else if (StatusEnum.DISABLE.getCode().equals(userInfo.getEnable())) {
            throw new BusinessException(ResultCode.USER_DISABLE, username + ResultCode.USER_DISABLE.getMsg());
        }
    }
}
