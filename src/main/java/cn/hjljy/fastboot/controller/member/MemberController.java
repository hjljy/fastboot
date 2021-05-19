package cn.hjljy.fastboot.controller.member;

import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.member.dto.MemberDto;
import cn.hjljy.fastboot.pojo.member.dto.RechargeParam;
import cn.hjljy.fastboot.service.member.IMemberService;
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
 * 会员控制器
 *
 * @author hjljy
 * @date 2021/05/17
 */
@RestController
@RequestMapping("/v1/member")
@Api(value = "会员操作相关接口", tags = "会员操作相关接口")
public class MemberController {

    @Autowired
    IMemberService memberService;

    @PostMapping("/recharge")
    @PreAuthorize("hasAnyAuthority('{authority=member:money:recharge}')")
    @ApiOperation(value = "会员充值（内部使用）")
    public ResultInfo<MemberDto> memberRecharge(@Validated @RequestBody RechargeParam param) {
        return ResultInfo.success(memberService.recharge(param));
    }
}
