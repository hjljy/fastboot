package cn.hjljy.fastboot.controller;


import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.autoconfig.security.UserInfo;
import cn.hjljy.fastboot.common.result.ResultInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-07-03
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @PostMapping("/user/info")
    @PreAuthorize("hasAuthority('{authority=sys:user:info}')")
    public ResultInfo<UserInfo> getUserInfo(){
        UserInfo userInfo = SecurityUtils.getUserInfo();
        return ResultInfo.success(userInfo);

    }
    @PostMapping("/user")
    @PreAuthorize("hasAuthority('{authority=sys:user:add}')")
    public ResultInfo<Boolean> getT2(@RequestBody UserInfo user){
        System.out.println("保存用户："+user.getNickName());
        return ResultInfo.success();
    }
}

