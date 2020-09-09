package cn.hjljy.fastboot.autoconfig.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author 海加尔金鹰
 * @apiNote 用户具体验证类
 * @since 2020/9/9 23:46
 **/
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        return null;
    }
}
