package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@ApiModel(value="SysUserPoDto对象", description="")
public class SysUserDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @NotNull(message = "用户机构不能为空")
    @ApiModelProperty(value = "用户机构ID")
    private Long orgId;

    @NotBlank(message = "用户账号不能为空")
    @ApiModelProperty(value = "用户账号")
    private String userName;

    @NotBlank(message = "用户昵称不能为空")
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @NotBlank(message = "用户密码不能为空")
    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "用户性别 -1保密(默认) 0女 1男 ")
    private Integer sex;

    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "用户描述")
    private String description;

    @ApiModelProperty(value = "返回的token")
    private String token;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @NotEmpty(message = "角色信息不能为空")
    @ApiModelProperty(value = "角色信息")
    private List<SysRoleDto> roles;

    @ApiModelProperty(value = "菜单权限")
    private List<SysMenuDto> menus;

    @ApiModelProperty(value = "是否禁用  0否(默认) 1是")
    private Boolean enable;




}
