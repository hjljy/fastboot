package cn.hjljy.fastboot.controller.member;


import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Insert;
import cn.hjljy.fastboot.common.aspect.validated.Select;
import cn.hjljy.fastboot.common.aspect.validated.Update;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.member.dto.MemberLevelDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberLevelParam;
import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import cn.hjljy.fastboot.service.member.IMemberLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/v1/member/level")
@Api(value = "会员等級列表", tags = "会员等級列表")
public class MemberLevelController {

    @Autowired
    IMemberLevelService memberLevelService;

    @PostMapping("/list")
    @ApiOperation(value = "查询机构所有会员等级信息(数据量少无需进行分页)")
    @PreAuthorize("hasAnyAuthority('{authority=member:level:list}')")
    public ResultInfo getMemberLevelList(@RequestBody  @Validated({Select.class}) MemberLevelParam param){
        List<MemberLevelDto> list= memberLevelService.selectOrgMemberLevelList(param.getOrgId());
        return new ResultInfo(list);
    }

    @PostMapping("add")
    @ApiOperation(value = "新增机构会员等级")
    @PreAuthorize("hasAnyAuthority('{authority=member:level:add}')")
    public ResultInfo addLevel(@RequestBody  @Validated({Insert.class}) MemberLevelDto dto){
        return new ResultInfo(memberLevelService.addLevel(dto));
    }

    @PostMapping("edit")
    @ApiOperation(value = "编辑机构会员等级")
    @PreAuthorize("hasAnyAuthority('{authority=member:level:edit}')")
    public ResultInfo editLevel(@RequestBody  @Validated({Update.class}) MemberLevelDto dto){
        return new ResultInfo(memberLevelService.editLevel(dto));
    }

    @PostMapping("del")
    @ApiOperation(value = "删除机构会员等级")
    @PreAuthorize("hasAnyAuthority('{authority=member:level:del}')")
    public ResultInfo delLevel(@RequestBody  @Validated({Update.class}) MemberLevelParam param){
        return new ResultInfo(memberLevelService.delLevel(param));
    }
}

