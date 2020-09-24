package cn.hjljy.fastboot.controller.demo;


import cn.hjljy.fastboot.autoconfig.security.UserInfo;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.demo.po.DemoPo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import cn.hjljy.fastboot.pojo.dto.DemoPoDto;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-07-03
 */
@RestController
@RequestMapping("/demo-po")
public class DemoController {

    @RequestMapping("/1")
    @Secured("ROLE_USER")
    public ResultInfo getDemo(String name) {
        if (SecurityContextHolder.getContext() == null) {
            return ResultInfo.error("没有用户信息");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        return ResultInfo.success(userInfo);
    }
    @RequestMapping("/2")
    @Secured("USER")
    public ResultInfo getDemo2(String name) {
        if (SecurityContextHolder.getContext() == null) {
            return ResultInfo.error("没有用户信息");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        return ResultInfo.success(userInfo);
    }
    @RequestMapping("/3")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public ResultInfo getDemo3(String name) {
        if (SecurityContextHolder.getContext() == null) {
            return ResultInfo.error("没有用户信息");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        return ResultInfo.success(userInfo);
    }
}

