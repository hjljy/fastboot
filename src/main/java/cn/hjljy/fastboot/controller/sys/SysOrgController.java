package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.autoconfig.aspect.validated.Insert;
import cn.hjljy.fastboot.autoconfig.aspect.validated.Select;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.sys.dto.SysOrgDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysOrgParam;
import cn.hjljy.fastboot.service.sys.ISysOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-01-26
 */
@RestController
@RequestMapping("/v1/sys/org")
@Api(value = "机构管理", tags = "机构管理")
public class SysOrgController {

    @Autowired
    ISysOrgService orgService;

    @GetMapping("/token/list")
    @ApiOperation(value = "根据token查询所有机构基础信息(非树形结构)")
    public ResultInfo<List<SysOrgDto>> getOrgListByUser() {
        return ResultInfo.success(orgService.getOrgListByUser());
    }

    @GetMapping("{orgId}/info")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:info}')")
    @ApiOperation(value = "根据orgId查询机构详细信息")
    public ResultInfo<SysOrgDto> getOrgInfoById(@PathVariable Long orgId) {
        return ResultInfo.success(orgService.getOrgInfoById(orgId));
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:edit}')")
    @ApiOperation(value = "编辑机构")
    public ResultInfo<Boolean> editOrg(@RequestBody @Validated({Select.class}) SysOrgDto param) {
        return ResultInfo.success(orgService.editOrgBaseInfo(param));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:add}')")
    @ApiOperation(value = "编辑机构")
    public ResultInfo<Boolean> addOrg(@RequestBody @Validated({Insert.class}) SysOrgDto param) {
        return ResultInfo.success(orgService.addOrg(param));
    }

    @PostMapping("/del/{orgId}")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:del}')")
    @ApiOperation(value = "删除机构")
    public ResultInfo<Boolean> delOrg(@PathVariable Long orgId) {
        return ResultInfo.success(orgService.deleteOrgByOrgId(orgId));
    }

    @PostMapping("/bind")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:bind}')")
    @ApiOperation(value = "机构绑定管理员")
    public ResultInfo<Boolean> bindAdmin(@RequestBody @Validated({Select.class}) SysOrgParam param) {
        return ResultInfo.success(orgService.bindAdmin(param.getOrgId(), param.getUserId()));
    }

}

