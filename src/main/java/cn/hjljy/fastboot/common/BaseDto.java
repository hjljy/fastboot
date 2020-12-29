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
    @NotNull(message ="机构ID必传")
    @ApiModelProperty(value = "机构ID")
    Long orgId;

    @ApiModelProperty(value = "查询关键字")
    String keywords;

    @ApiModelProperty(value = "多少页")
    Integer pageNo;

    @ApiModelProperty(value = "每页多少条数据")
    Integer pageNum;

    @ApiModelProperty(value = "api版本")
    String apiVersion;

    @ApiModelProperty(value = "请求来源 web,ios,windows,h5,andiron")
    String source;

    public Page createPage(){
        return new Page(pageNo, pageNum);
    }
}
