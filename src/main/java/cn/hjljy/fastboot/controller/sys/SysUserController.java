package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.aspect.validated.Insert;
import cn.hjljy.fastboot.common.aspect.validated.Select;
import cn.hjljy.fastboot.common.aspect.validated.Update;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.sys.dto.PasswordParam;
import cn.hjljy.fastboot.pojo.sys.dto.PhoneParam;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserParam;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @PostMapping("/page")
    @PreAuthorize("hasAuthority('{authority=sys:user:list}')")
    @ApiOperation(value = "分页查询用户信息")
    public ResultInfo<IPage<SysUserDto>> getSysUserPage(@RequestBody SysUserParam param) {
        return ResultInfo.success(userService.getSysUserInfoPage(param));
    }

    @GetMapping("/token/info")
    @ApiOperation(value = "根据token查询用户详细信息")
    public ResultInfo<SysUserDto> getSysUserInfoByToken() {
        Long userId = SecurityUtils.getUserId();
        return ResultInfo.success(userService.getUserDetailInfoByUserId(userId));
    }

    @GetMapping("/info")
    @ApiOperation(value = "根据ID查询用户详细信息")
    @PreAuthorize("hasAuthority('{authority=sys:user:list}')")
    public ResultInfo<SysUserDto> getSysUserInfo(@Validated({Select.class}) SysUserParam param) {
        return ResultInfo.success(userService.getUserDetailInfoByUserId(param.getUserId()));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('{authority=sys:user:add}')")
    @ApiOperation(value = "新增用户")
    public ResultInfo<Object> addSysUserInfo(@RequestBody @Validated({Insert.class}) SysUserDto param) {
        userService.addSysUserInfo(param);
        return ResultInfo.success();
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('{authority=sys:user:edit}')")
    @ApiOperation(value = "更新用户信息")
    public ResultInfo<Object> updateSysUserInfo(@RequestBody @Validated({Update.class}) SysUserDto param) {
        userService.updateSysUserInfo(param);
        return ResultInfo.success();
    }

    @PostMapping("/disable")
    @PreAuthorize("hasAuthority('{authority=sys:user:disable}')")
    @ApiOperation(value = "禁用用户")
    public ResultInfo<Object> disableSysUser(@RequestBody @Validated({Select.class}) SysUserParam param) {
        userService.disableSysUser(param);
        return ResultInfo.success();
    }

    @PostMapping("/del")
    @PreAuthorize("hasAuthority('{authority=sys:user:del}')")
    @ApiOperation(value = "删除用户")
    public ResultInfo<Object> delSysUser(@RequestBody @Validated({Select.class}) SysUserParam param) {
        userService.removeSysUser(param.getUserId());
        return ResultInfo.success();
    }

    @PostMapping("/resetPassword")
    @PreAuthorize("hasAuthority('{authority=sys:user:rpwd}')")
    @ApiOperation(value = "重置用户密码")
    public ResultInfo<Object> resetPassword(@RequestBody @Validated({Select.class}) SysUserParam param) {
        userService.resetUserPassword(param.getUserId());
        return ResultInfo.success();
    }

    @PostMapping("/editPassword")
    @ApiOperation(value = "修改当前用户密码(无需权限控制)")
    public ResultInfo<Object> editPassword(@RequestBody @Validated({Select.class}) PasswordParam param) {
        userService.editUserPassword(param);
        ResultInfo<Object> resultInfo = new ResultInfo<>();
        resultInfo.setMsg("修改账号密码成功，请重新登录");
        return resultInfo;
    }

    @PostMapping("/bindPhone")
    @ApiOperation(value = "当前用户账号绑定手机号(无需权限控制)")
    public ResultInfo<Object> editPhone(@RequestBody @Validated({Select.class}) PhoneParam param) {
        userService.bindPhone(param.getPhone());
        ResultInfo<Object> resultInfo = new ResultInfo<>();
        resultInfo.setMsg("关联手机号码成功，请重新登录");
        return resultInfo;
    }
}

