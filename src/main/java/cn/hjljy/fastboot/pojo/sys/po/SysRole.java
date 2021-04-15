package cn.hjljy.fastboot.pojo.sys.po;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

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
@ApiModel(value = "SysRolePo对象", description = "")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色机构ID")
    private Long orgId;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.NEVER,fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField(updateStrategy = FieldStrategy.NEVER,fill = FieldFill.INSERT)
    private Long createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(insertStrategy = FieldStrategy.NEVER,fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField(insertStrategy = FieldStrategy.NEVER,fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @ApiModelProperty(value = "是否删除  0否(默认) 1是")
    @TableField(fill = FieldFill.UPDATE)
    @TableLogic
    private Integer status;


}
