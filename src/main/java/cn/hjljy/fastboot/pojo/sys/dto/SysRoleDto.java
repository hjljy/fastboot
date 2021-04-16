package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.pojo.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysRolePoDto对象")
public class SysRoleDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色Id不能为空",groups = {Update.class})
    private Integer id;

    @ApiModelProperty(value = "角色机构ID")
    private Long orgId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty
    private List<SysMenuDto> menus;

}
