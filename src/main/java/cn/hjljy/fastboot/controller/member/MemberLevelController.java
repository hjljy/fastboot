package cn.hjljy.fastboot.controller.member;


import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.member.dto.MemberLevelDto;
import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import cn.hjljy.fastboot.service.member.IMemberLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ApiOperation(value = "查询机构所有会员等级信息（不进行分页）")
    public ResultInfo<List<MemberLevelDto>> getMemberLevelList(@RequestBody  @Validated BaseDto dto){
        List<MemberLevelDto> list= memberLevelService.selectOrgMemberLevelList(dto.getOrgId());
        return new ResultInfo<>(list);
    }

    @PostMapping("addLevel")
    public ResultInfo<Integer> addLevel(@RequestBody  @Validated MemberLevelDto dto){
        return new ResultInfo<>(memberLevelService.addLevel(dto));
    }
}

