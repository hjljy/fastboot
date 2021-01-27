package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.service.sys.ISysOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "根据token查询用户机构信息")
    public ResultInfo getOrgListByUser() {
        return ResultInfo.success( orgService.getOrgListByUser());
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:edit}')")
    @ApiOperation(value = "编辑机构")
    public ResultInfo editOrg() {
        return ResultInfo.success( orgService.getOrgListByUser());
    }

    @PostMapping("/del")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:del}')")
    @ApiOperation(value = "删除机构")
    public ResultInfo delOrg() {
        return ResultInfo.success( orgService.getOrgListByUser());
    }

    @PostMapping("/disable")
    @PreAuthorize("hasAnyAuthority('{authority=sys:org:disable}')")
    @ApiOperation(value = "禁用机构")
    public ResultInfo disableOrg() {
        return ResultInfo.success( orgService.getOrgListByUser());
    }

}

