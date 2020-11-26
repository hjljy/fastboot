package cn.hjljy.fastboot.pojo.sys.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
@ApiModel(value="SysRolePo对象", description="")
public class SysRole implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "角色ID")
    private Integer id;

    @ApiModelProperty(value = "角色机构ID")
    private Integer orgId;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除  0否(默认) 1是")
    @TableLogic
    private Integer status;


}