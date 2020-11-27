package cn.hjljy.fastboot.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yichaofan
 * @apiNote 基础类
 * @since 2020/11/26 18:14
 */
@Data
public class BaseDto {
    @NotNull(message ="机构ID必传")
    @ApiModelProperty(value = "机构ID")
    String orgId;

    @ApiModelProperty(value = "多少页")
    Integer pageNo;

    @ApiModelProperty(value = "每页多少条数据")
    Integer pageNum;
}
