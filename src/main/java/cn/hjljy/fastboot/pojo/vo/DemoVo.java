package cn.hjljy.fastboot.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author yichaofan
 * @date 2020/6/4 18:24
 * @apiNote
 */
@ApiModel(value = "demo")
@Data
public class DemoVo {

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "名称",required = true)
    private String name;

    @ApiModelProperty(value = "人物ID")
    private Integer persionId;

}
