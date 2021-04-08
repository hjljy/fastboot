package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.common.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysOrgMenuDto对象")
public class SysOrgMenuDto extends BaseDto<SysOrgMenuDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "机构ID")
    private Integer orgId;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

}
