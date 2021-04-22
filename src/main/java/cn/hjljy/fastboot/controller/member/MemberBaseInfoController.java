package cn.hjljy.fastboot.controller.member;


import cn.hjljy.fastboot.common.aspect.validated.Update;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoParam;
import cn.hjljy.fastboot.pojo.member.dto.MemberDto;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/v1/member/baseInfo")
@Api(value = "会员列表", tags = "会员列表信息")
public class MemberBaseInfoController {

    @Autowired
    IMemberBaseInfoService memberBaseInfoService;

    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('{authority=member:info:list}')")
    @ApiOperation(value = "分页查询会员信息")
    public ResultInfo<IPage<MemberBaseInfoDto>> getMemberBaseInfoPageList(@Validated MemberBaseInfoParam param) {
        return ResultInfo.success(memberBaseInfoService.getMemberBaseInfoPageList(param));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('{authority=member:info:add}')")
    @ApiOperation(value = "新增会员信息")
    public ResultInfo<Integer> addMember(@RequestBody @Validated MemberBaseInfoDto dto) {
        return ResultInfo.success(memberBaseInfoService.addMember(dto));
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('{authority=member:info:edit}')")
    @ApiOperation(value = "编辑会员信息")
    public ResultInfo<Boolean> editMember(@RequestBody @Validated({Update.class}) MemberBaseInfoDto dto) {
        return ResultInfo.success(memberBaseInfoService.editMember(dto));
    }

    @DeleteMapping("/del/{memberId}")
    @PreAuthorize("hasAnyAuthority('{authority=member:info:del}')")
    @ApiOperation(value = "删除会员信息")
    public ResultInfo<Boolean> delMember(@PathVariable Long memberId) {
        return ResultInfo.success(memberBaseInfoService.delMember(memberId));
    }

    @GetMapping("/info/{memberId}")
    @PreAuthorize("hasAnyAuthority('{authority=member:info:detail}')")
    @ApiOperation(value = "会员详细信息")
    public ResultInfo<MemberDto> memberInfo(@PathVariable Long memberId) {
        return ResultInfo.success(memberBaseInfoService.getMemberDto(memberId));
    }

}

