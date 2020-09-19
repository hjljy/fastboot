package cn.lsbly.bootService.pojo.sitemap.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 海加尔金鹰（www.lsbly.cn）
 * @since 2020-09-19
 */
@Data
@ApiModel(value="WebSitemapDto对象", description="")
public class WebSitemapDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "网站名称")
    @NotNull(message = "网站名称不能为空")
    private String name;

    @ApiModelProperty(value = "网站url地址")
    @NotNull(message = "网站名称不能为空")
    private String url;

    @ApiModelProperty(value = "网站图标")
    private String icon;

    @ApiModelProperty(value = "网站描述")
    private String webDesc;

    @ApiModelProperty(value = "添加人名称")
    private String username;


}
