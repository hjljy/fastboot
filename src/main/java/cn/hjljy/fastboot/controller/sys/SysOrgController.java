package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.common.aspect.validated.Select;
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

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('{authority=sys:user:list}','{authority=sys:role:list}','{authority=sys:org:list}')")
    @ApiOperation(value = "根据token查询用户机构信息(非树形结构)")
    public ResultInfo<List<SysOrgDto>> getOrgListByUser() {
        return new ResultInfo<List<SysOrgDto>>().success( orgService.getOrgListByUser());
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:edit}')")
    @ApiOperation(value = "编辑机构")
    public ResultInfo<Boolean> editOrg(@RequestBody @Validated({Select.class})  SysOrgDto param) {
        return new ResultInfo<Boolean>().success(orgService.editOrgBaseInfo(param));
    }

    @PostMapping("/del/{orgId}")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:del}')")
    @ApiOperation(value = "删除机构")
    public ResultInfo<Boolean> delOrg(@PathVariable Long orgId ) {
        return new ResultInfo<Boolean>().success( orgService.deleteOrgByOrgId(orgId));
    }

    @PostMapping("/disable")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:disable}')")
    @ApiOperation(value = "禁用机构")
    public ResultInfo<Boolean> disableOrg(@RequestBody @Validated({Select.class}) SysOrgParam param) {
        return new ResultInfo<Boolean>().success( orgService.disableOrg(param.getId(),param.getEnable()));
    }

}

