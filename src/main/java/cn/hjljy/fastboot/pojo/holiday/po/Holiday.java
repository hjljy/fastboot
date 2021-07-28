package cn.hjljy.fastboot.pojo.holiday.po;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Holiday对象", description="")
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "日期")
    private String time;

    @ApiModelProperty(value = "0 正常补班日 1 正常休息日  2法定节假日 ")
    private Integer type;

    @ApiModelProperty(value = "年份")
    private String year;


}
