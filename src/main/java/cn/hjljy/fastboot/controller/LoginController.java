package cn.hjljy.fastboot.controller;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;

import javax.servlet.http.HttpServletRequest;


/**
 * @author : yichaofan
 * @apiNote :登录路由
 * @since : 2020/9/11 18:19
 */
@RestController
public class LoginController {


    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(value = "login")
    public ResultInfo login(  String username,String password, HttpServletRequest request)  {
        SecurityUtils.login(username,password,authenticationManager);

        // 系统登录认证
//        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);

        return ResultInfo.success(123);
    }
}
