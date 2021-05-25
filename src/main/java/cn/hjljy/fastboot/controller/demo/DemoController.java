package cn.hjljy.fastboot.controller.demo;


import cn.hjljy.fastboot.autoconfig.security.UserInfo;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.demo.po.DemoPo;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
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

    @PostMapping("/t1")
    @PreAuthorize("hasAuthority('{authority=sys:menu:add}')")
    public String getDemo(String name) {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

//        return ResultInfo.success(userInfo);
        return userInfo.toString();
    }

    @GetMapping(value = "test",params = {"version=1"},headers = {"version=1"},consumes = {"Content-Type=application/v1.json"})
    public String getT2() {
        return "123456";
    }
}

