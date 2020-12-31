package cn.hjljy.fastboot.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yichaofan
 * @apiNote 基础参数类
 * @since 2020/11/26 18:14
 */
@Data
public class BaseDto {

    @ApiModelProperty(value = "机构ID")
    Long orgId=-1L;

    @ApiModelProperty(value = "查询关键字")
    String keywords;

    @ApiModelProperty(value = "token")
    String token;

    @ApiModelProperty(value = "多少页")
    Integer pageNo = 1;

    @ApiModelProperty(value = "每页多少条数据")
    Integer pageNum = 20;

    @ApiModelProperty(value = "api版本")
    String apiVersion = "v1";

    @ApiModelProperty(value = "请求来源 web,ios,windows,h5,andiron")
    String source="WEB";

    public Page createPage(){
        return new Page(pageNo, pageNum);
    }
}
