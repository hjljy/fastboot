package cn.hjljy.fastboot.controller;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.autoconfig.security.UserInfo;
import cn.hjljy.fastboot.common.result.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

/**
 * @author : yichaofan
 * @apiNote :登录路由
 * @since : 2020/9/11 18:19
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public ResultInfo login(@RequestBody Principal principal, HttpServletRequest request)  {

        WebAuthenticationDetails authenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
        System.out.println(principal);
        // 系统登录认证
//        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);

        return ResultInfo.success(null);
    }
}
