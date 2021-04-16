package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.pojo.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Select;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value = "PasswordParam对象")
public class PasswordParam extends BaseDto {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空", groups = Select.class)
    private Long userId;

    @ApiModelProperty(value = "旧密码", required = true)
    @NotNull(message = "旧密码不能为空", groups = Select.class)
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotNull(message = "新密码不能为空", groups = Select.class)
    private String newPassword;

    @ApiModelProperty(value = "重复密码", required = true)
    @NotNull(message = "重复密码不能为空", groups = Select.class)
    private String rPassword;

}
