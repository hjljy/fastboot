package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.sys.dto.SysRoleDto;
import cn.hjljy.fastboot.service.sys.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/v1/sys/role")
@Api(value = "角色管理", tags = "角色管理")
public class SysRoleController {
    @Resource
    ISysRoleService roleService;

    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('{authority=sys:user:list}','{authority=sys:role:list}')")
    @ApiOperation(value = "查询所有角色信息")
    public ResultInfo<List<SysRoleDto>> getSysUserPage(@RequestBody @Validated SysRoleDto param) {
        return new ResultInfo<List<SysRoleDto>>().success(roleService.list(param));
    }
}

