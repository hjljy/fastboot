package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-06-24
 */
@RestController
@RequestMapping("/sys")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('{authority=sys:user:info}')")
    public ResultInfo<SysUser> getUser(@PathVariable Long userId){
        return ResultInfo.success(userService.getById(userId));
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('{authority=sys:user:add}')")
    public ResultInfo<Boolean> addUser(@RequestBody @Validated  SysUser sysUser){
        return ResultInfo.success(userService.addUser(sysUser));
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('{authority=sys:user:del}')")
    public ResultInfo<Boolean> delUser(@PathVariable Long userId){
        return ResultInfo.success(userService.removeById(userId));
    }

    @PutMapping("/user")
    @PreAuthorize("hasAuthority('{authority=sys:user:add}')")
    public ResultInfo<Boolean> updateUser(@RequestBody @Validated SysUser sysUser){
        return ResultInfo.success(userService.updateUser(sysUser));
    }
}

