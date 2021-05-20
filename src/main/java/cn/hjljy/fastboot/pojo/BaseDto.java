package cn.hjljy.fastboot.pojo;

import cn.hjljy.fastboot.common.enums.RequestSourceEnum;
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

    @ApiModelProperty(value = "所属机构ID")
    private Long orgId;

    @ApiModelProperty(value = "多少页")
    @JsonIgnore
    Integer pageNo = 1;

    @ApiModelProperty(value = "每页多少条数据")
    @JsonIgnore
    Integer pageNum = 20;

    @ApiModelProperty(value = "请求来源")
    @JsonIgnore
    RequestSourceEnum requestSource;

    public <T> Page<T> createPage() {
        return new Page<>(pageNo, pageNum);
    }
}
