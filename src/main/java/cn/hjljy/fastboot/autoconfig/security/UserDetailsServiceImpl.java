package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.common.constant.Oauth2Constant;
import cn.hjljy.fastboot.common.enums.UserTypeEnum;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.sys.ISysMenuService;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hjljy
 * @apiNote 用户具体验证类
 * @since 2020/9/11
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    ISysUserService userService;

    @Autowired
    ISysMenuService menuService;

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
     * @throws UsernameNotFoundException 用户不存在异常
     */
    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        //由于关闭了allowFormAuthenticationForClients 这个选项，所以需要判断传进来的是否是clientName
        if (username.equals(Oauth2Constant.CLIENT_ID)) {
            ClientDetails details = clientDetailsService.loadClientByClientId(username);
            return new UserInfo(details.getClientId(), details.getClientSecret(), details.getAuthorities());
        }
        //TODO 根据账号获取数据库里面的用户信息,权限信息
        SysUser sysUser = userService.getByUsername(username);
        checkUser(sysUser);
        List<SysMenu> list;
        if (sysUser.isSysAdmin()) {
            list = menuService.list();
        } else {
            list = menuService.listByUserId(sysUser.getId());
        }
        List<String> strings = list.stream().map(SysMenu::getAuthority).collect(Collectors.toList());
        String[] array = new String[strings.size()];
        strings.toArray(array);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(array);
        UserInfo user = new UserInfo(username, sysUser.getPassword(), authorityList);
        user.setEmail(sysUser.getEmail());
        user.setNickName(sysUser.getNickName());
        user.setUserId(sysUser.getId());
        return user;
    }

    private void checkUser(SysUser sysUser) {
        if (null == sysUser) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_FOUND.getMsg());
        }
        if (sysUser.getEnable()) {
            throw new UsernameNotFoundException(ResultCode.USER_DISABLE.getMsg());
        }
    }
}
