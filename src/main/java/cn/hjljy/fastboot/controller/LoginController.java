package cn.hjljy.fastboot.controller;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.autoconfig.security.UserInfo;
import cn.hjljy.fastboot.common.result.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

    /***
     * 描述: 单纯security的登录，集成auth2之后无法使用 使用/oauth/token接口进行登录
     * <p>
     * 作者: yichaofan
     * 日期: 2020/10/15 18:16
     *
     * @param params 请求参数
     * @return cn.hjljy.fastboot.common.result.ResultInfo
     **/
    @Deprecated
    @PostMapping(value = "login")
    public ResultInfo login(@RequestBody Map<String,String> params)  {
        UserInfo userInfo = SecurityUtils.login(params.get("username"), params.get("password"), authenticationManager);
        return new ResultInfo(userInfo);
    }

}
