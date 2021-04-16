package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.pojo.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Insert;
import cn.hjljy.fastboot.common.aspect.validated.Update;
import cn.hjljy.fastboot.common.enums.SexEnum;
import cn.hjljy.fastboot.common.enums.sys.SysUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUserPoDto对象")
public class SysUserDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户账号ID不能为空", groups = Update.class)
    private Long id;

    @NotNull(message = "用户机构不能为空")
    @ApiModelProperty(value = "用户机构ID")
    private Long orgId;

    @NotBlank(message = "用户账号不能为空", groups = Insert.class)
    @ApiModelProperty(value = "用户账号")
    private String userName;

    @NotBlank(message = "用户昵称不能为空")
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @NotBlank(message = "用户密码不能为空", groups = Insert.class)
    @ApiModelProperty(value = "用户密码")
    private String password;
    /**
     * @see cn.hjljy.fastboot.common.enums.sys.SysUserTypeEnum
     */
    @ApiModelProperty(value = "用户类型")
    private SysUserTypeEnum userType;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "用户性别 -1保密(默认) 0女 1男 ")
    private SexEnum sex;

    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthday;

    @Size(max = 260, message = "用户描述过长，请简单描述")
    @ApiModelProperty(value = "用户描述")
    private String description;

    @ApiModelProperty(value = "返回的token")
    private String token;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "角色信息")
    private List<SysRoleDto> roles;

    @ApiModelProperty(value = "角色Id集合")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "菜单权限")
    private List<SysMenuDto> menus;

    @ApiModelProperty(value = "是否启用  0是(默认) 1否")
    private Integer enable;


}
