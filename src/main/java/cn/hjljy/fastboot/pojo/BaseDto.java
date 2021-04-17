package cn.hjljy.fastboot.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yichaofan
 * @apiNote 基础参数类
 * @since 2020/11/26 18:14
 */
@Data
public class BaseDto {

    @ApiModelProperty(value = "多少页")
    @JsonIgnore
    Integer pageNo = 1;

    @ApiModelProperty(value = "每页多少条数据")
    @JsonIgnore
    Integer pageNum = 20;

    @ApiModelProperty(value = "api版本")
    @JsonIgnore
    String apiVersion = "v1";

    @ApiModelProperty(value = "请求来源 WEB,IOS,WINDOWS,H5")
    @JsonIgnore
    String requestSource = "WEB";

    public <T> Page<T> createPage() {
        return new Page<>(pageNo, pageNum);
    }
}
