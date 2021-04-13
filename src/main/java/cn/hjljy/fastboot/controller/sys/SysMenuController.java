package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.autoconfig.security.UserInfo;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hjljy.fastboot.service.sys.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-11
 */
@RestController
@RequestMapping("v1/sys/menu")
@Api(value = "菜单权限管理", tags = "菜单权限管理")
public class SysMenuController {

    @Autowired
    ISysMenuService sysMenuService;

    @GetMapping("/token/list")
    @ApiOperation(value = "根据token获取用户菜单权限信息")
    public ResultInfo<List<SysMenu>> getSysMenuList(){
        UserInfo userInfo = SecurityUtils.getUserInfo();
        return ResultInfo.success(sysMenuService.getUserMenuListInfo(userInfo.getUserId(), userInfo.getUserType(), userInfo.getOrgId()));
    }
}

