package cn.hjljy.fastboot.controller.member;


import cn.hjljy.fastboot.common.aspect.validated.Update;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoParam;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/page")
    @PreAuthorize("hasAnyAuthority('{authority=member:info:list}')")
    @ApiOperation(value = "分页查询会员信息")
    public ResultInfo getMemberBaseInfoPageList(@RequestBody @Validated MemberBaseInfoParam param) {
        IPage<MemberBaseInfoDto> list = memberBaseInfoService.getMemberBaseInfoPageList(param.getOrgId(), param.getKeywords(), param.getLevelId(), param.getPageNo(), param.getPageNum());
        return new ResultInfo(list);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('{authority=member:info:add}')")
    @ApiOperation(value = "新增会员信息")
    public ResultInfo addMember(@RequestBody @Validated MemberBaseInfoDto dto) {
        return new ResultInfo(memberBaseInfoService.addMember(dto));
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('{authority=member:info:edit}')")
    @ApiOperation(value = "编辑会员信息")
    public ResultInfo editMember(@RequestBody @Validated({Update.class}) MemberBaseInfoDto dto) {
        return new ResultInfo(memberBaseInfoService.editMember(dto));
    }

    @PostMapping("/del")
    @PreAuthorize("hasAnyAuthority('{authority=member:info:del}')")
    @ApiOperation(value = "删除会员信息")
    public ResultInfo delMember(@RequestBody @Validated({Update.class}) MemberBaseInfoParam param) {
        return new ResultInfo(memberBaseInfoService.delMember(param.getMemberId()));
    }
}

