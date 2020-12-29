package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberListDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserParam;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.sys.ISysUserService;
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
 * 用戶列表信息
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-09
 */
@RestController
@RequestMapping("/v1/sys/user")
@Api(value = "用户管理", tags = "用户管理")
public class SysUserController {

    @Autowired
    ISysUserService userService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询用户信息")
    public ResultInfo<IPage<SysUserDto>> getSysUserPage(@RequestBody @Validated SysUserParam param) {
        IPage<SysUserDto> page = userService.getSysUserInfoPage(param);
        return new ResultInfo<>(page);
    }
}

