package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.common.BaseDto;
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
@ApiModel(value = "SysUserParam对象")
public class SysUserParam extends BaseDto {

    @ApiModelProperty(value = "用户角色ID")
    private Long roleId;

    @ApiModelProperty(value = "用户机构ID")
    private Long orgId;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空", groups = Select.class)
    private Long userId;

    @ApiModelProperty(value = "是否启用 0是 1否 ")
    private int enable;

}
