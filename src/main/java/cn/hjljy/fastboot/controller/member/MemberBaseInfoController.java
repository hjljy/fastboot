package cn.hjljy.fastboot.controller.member;


import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberListDto;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/v1/member")
@Api(value = "会员列表", tags = "会员列表信息")
public class MemberBaseInfoController {

    @Autowired
    IMemberBaseInfoService memberBaseInfoService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询会员信息")
    public ResultInfo getMemberBaseInfoPageList(@RequestBody @Validated MemberListDto dto) {
        IPage<MemberBaseInfoDto> list = memberBaseInfoService.getMemberBaseInfoPageList(dto.getOrgId(), dto.getKeywords(), dto.getLevelId(), dto.getPageNo(), dto.getPageNum());
        return new ResultInfo(list);
    }

    @PostMapping("/addMember")
    @ApiOperation(value = "新增会员信息")
    public ResultInfo addMember(@RequestBody @Validated MemberBaseInfoDto dto) {
        return new ResultInfo(memberBaseInfoService.addMember(dto));
    }
}
