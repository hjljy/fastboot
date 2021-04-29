package cn.hjljy.fastboot.controller.member;


import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.member.dto.MemberUpvDto;
import cn.hjljy.fastboot.service.member.IMemberUpvService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{orgId}")
    @ApiOperation(value = "查询机构所有会员成长值变化规则")
    @PreAuthorize("hasAnyAuthority('{authority=member:right:list}')")
    public ResultInfo<MemberUpvDto> getByOrg(@PathVariable String orgId) {
        MemberUpvDto upvDto = memberUpvService.getByOrgId(orgId);
        ResultInfo<MemberUpvDto> success = new ResultInfo<>(upvDto);
        if (null == upvDto) {
            success.setMsg("当前未设置成长值变化规则");
        }
        return success;
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑机构所有会员成长值变化规则")
    @PreAuthorize("hasAnyAuthority('{authority=member:right:edit}')")
    public ResultInfo<Boolean> edit(@RequestBody @Validated MemberUpvDto dto) {
        return new ResultInfo<>(memberUpvService.editUpv(dto));
    }
}

