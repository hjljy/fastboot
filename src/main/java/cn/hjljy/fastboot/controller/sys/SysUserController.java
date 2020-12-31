package cn.hjljy.fastboot.controller.sys;


import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserParam;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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

    @PostMapping("/page")
    @ApiOperation(value = "分页查询用户信息")
    public ResultInfo<IPage<SysUserDto>> getSysUserPage(@RequestBody @Validated SysUserParam param) {
        IPage<SysUserDto> page = userService.getSysUserInfoPage(param);
        return new ResultInfo<>(page);
    }

    @GetMapping("/token/info")
    @ApiOperation(value = "根据token查询用户详细信息")
    public ResultInfo<SysUserDto> getSysUserInfoByToken(SysUserParam param) {
        if(StringUtils.isNotBlank(param.getToken())){

        }
        return null;
    }

    @GetMapping("/info")
    @ApiOperation(value = "根据ID查询用户详细信息")
    public ResultInfo<SysUserDto> getSysUserInfo(SysUserParam param) {
        Assert.notNull(param.getUserId(),"用户ID不能为空");
        SysUserDto user =  userService.getUserDetailInfoByUserId(param.getUserId());
        return new ResultInfo<>(user);
    }
}

