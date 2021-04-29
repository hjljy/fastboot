package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.pojo.BaseDto;
import cn.hjljy.fastboot.autoconfig.aspect.validated.Select;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysOrgParam对象")
public class SysOrgParam extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "机构ID不能为空", groups = Select.class)
    @ApiModelProperty(value = "机构ID")
    private Long orgId;

    @NotNull(message = "用户ID不能为空", groups = Select.class)
    @ApiModelProperty(value = "用户ID")
    private Long userId;
}
