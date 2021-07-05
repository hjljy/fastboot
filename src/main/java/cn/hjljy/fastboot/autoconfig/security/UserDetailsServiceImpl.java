package cn.hjljy.fastboot.autoconfig.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjljy
 * @apiNote 用户具体验证类
 * @since 2020/9/11
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * 这里根据传进来的用户账号进行用户信息的构建
     * 通常的做法是
     *  1 根据username查询数据库对应的用户信息
     *  2 根据用户信息查询出用户权限信息  例如菜单添加权限  sys:menu:add
     *  3 根据用户账号，密码，权限构建对应的UserDetails对象返回
     * 这里实际上是没有进行用户认证功能的，真正的验证是在UsernamePasswordAuthenticationFilter对象当中
     * UsernamePasswordAuthenticationFilter对象会自动根据前端传入的账号信息和UserDetails对象对比进行账号的验证
     * 通常情况下，已经满足常见的使用常见，不过如果有特殊需求，需要使用自己实现的具体认证方式，可以继承UsernamePasswordAuthenticationFilter对象
     * 重写attemptAuthentication 方法和successfulAuthentication方法
     * 最后在WebSecurityConfiguration里面添加自己的过滤器即可
     * @param username 用户账号
     * @return UserInfo
     * @throws UsernameNotFoundException 用户不存在异常
     */
    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 根据账号获取数据库里面的用户信息,权限信息
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("sys:user:info","sys:user:add");
        String password = SecurityUtils.encryptPassword("123456");
        UserInfo user =new UserInfo(username,password,authorityList);
        user.setEmail("hjljy@outlook.com");
        user.setNickName("海加尔金鹰");
        user.setUserId(10000000000000L);
        return user;
    }
}
