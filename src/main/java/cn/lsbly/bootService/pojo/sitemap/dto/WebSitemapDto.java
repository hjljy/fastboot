package cn.lsbly.bootService.pojo.sitemap.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author 海加尔金鹰（www.lsbly.cn）
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="WebSitemapDto对象", description="")
public class WebSitemapDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "网站名称")
    private String name;

    @ApiModelProperty(value = "网站url地址")
    private String url;

    @ApiModelProperty(value = "网站图标")
    private String icon;

    @ApiModelProperty(value = "网站描述")
    private String desc;

    @ApiModelProperty(value = "添加人名称")
    private String username;


}
