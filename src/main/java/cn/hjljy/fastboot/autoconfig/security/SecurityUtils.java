package cn.hjljy.fastboot.autoconfig.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author : yichaofan
 * @apiNote :用户信息工具类
 * @since : 2020/9/11 18:22
 */
@Slf4j
public class SecurityUtils {

    public static void login(String username, String password, AuthenticationManager authenticationManager){
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(username,password );
        Authentication authenticate = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
}
