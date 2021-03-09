package cn.hjljy.fastboot.controller.member;


import cn.hjljy.fastboot.common.aspect.validated.Update;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.member.dto.MemberUpvDto;
import cn.hjljy.fastboot.pojo.member.po.MemberUpv;
import cn.hjljy.fastboot.service.member.IMemberUpvService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员权益成长值获取扣减计算规则表 前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/v1/member/upv")
public class MemberUpvController {

    @Autowired
    IMemberUpvService memberUpvService;

    @GetMapping("/info/{orgId}")
    @ApiOperation(value = "查询机构所有会员成长值变化规则")
    @PreAuthorize("hasAnyAuthority('{authority=member:upv:info}')")
    public ResultInfo getByOrg(@PathVariable(value = "orgId", required = false) String orgId) {
        MemberUpvDto upvDto = memberUpvService.getByOrgId(orgId);
        ResultInfo success = ResultInfo.success(memberUpvService.getByOrgId(orgId));
        if (null == upvDto) {
            success.setMsg("当前未设置成长值变化规则");
        }
        return success;
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑机构所有会员成长值变化规则")
    @PreAuthorize("hasAnyAuthority('{authority=member:upv:edit}')")
    public ResultInfo edit(@RequestBody @Validated MemberUpvDto dto) {
        return ResultInfo.success(memberUpvService.editUpv(dto));
    }
}

