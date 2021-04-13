package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.aspect.validated.Insert;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.sys.dto.SysRoleDto;
import cn.hjljy.fastboot.service.sys.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/token/list")
    @ApiOperation(value = "根据token查询能看到的所有角色信息")
    public ResultInfo<List<SysRoleDto>> getSysRoleList() {
        return ResultInfo.success(roleService.listByOrgId(SecurityUtils.getOrgId()));
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('{authority=sys:user:list}','{authority=sys:role:list}')")
    @ApiOperation(value = "查询所有角色信息")
    public ResultInfo<List<SysRoleDto>> getSysRolePage(@RequestBody SysRoleDto param) {
        return ResultInfo.success(roleService.list(param));
    }

    @GetMapping("/{roleId}")
    @PreAuthorize("hasAnyAuthority('{authority=sys:role:list}')")
    @ApiOperation(value = "获取角色详细信息")
    public ResultInfo<SysRoleDto> roleInfo(@PathVariable Integer roleId) {
        return ResultInfo.success(roleService.getRoleInfo(roleId));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('{authority=sys:role:add}')")
    @ApiOperation(value = "新增角色信息")
    public ResultInfo<Boolean> addRole(@RequestBody @Validated({Insert.class}) SysRoleDto param) {
        return ResultInfo.success(roleService.add(param));
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('{authority=sys:role:edit}')")
    @ApiOperation(value = "编辑角色信息")
    public ResultInfo<Boolean> editRole(@RequestBody @Validated SysRoleDto param) {
        return ResultInfo.success(roleService.edit(param));
    }

    @GetMapping("/del/{roleId}")
    @PreAuthorize("hasAnyAuthority('{authority=sys:role:del}')")
    @ApiOperation(value = "删除角色信息")
    public ResultInfo<Boolean> delRole(@PathVariable Integer roleId) {
        return ResultInfo.success(roleService.del(roleId));
    }
}

