package cn.hjljy.fastboot.controller;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.autoconfig.security.UserInfo;
import cn.hjljy.fastboot.common.result.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


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
    public ResultInfo login(@RequestBody Map<String,String> params)  {
        UserInfo userInfo = SecurityUtils.login(params.get("username"), params.get("password"), authenticationManager);
        return ResultInfo.success(userInfo);
    }

    @PostMapping(value = "/oauth/token2")
    public ResultInfo token(@RequestBody Map<String,String> params)  {
      //  UserInfo userInfo = SecurityUtils.login(params.get("username"), params.get("password"), authenticationManager);
        return ResultInfo.success();
    }
}
