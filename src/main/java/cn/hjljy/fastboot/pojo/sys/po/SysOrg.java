package cn.hjljy.fastboot.pojo.sys.po;

import cn.hjljy.fastboot.common.enums.sys.SysOrgStateEnum;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SysOrg对象")
@TableName("sys_org")
public class SysOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构ID")
    private Long id;

    @ApiModelProperty(value = "父级机构ID")
    private Long pid;

    @ApiModelProperty(value = "机构名称")
    private String name;

    @ApiModelProperty(value = "机构地址")
    private String address;

    @ApiModelProperty(value = "机构联系电话")
    private String phone;

    @ApiModelProperty(value = "机构logo")
    private String logo;

    /**
     * @see cn.hjljy.fastboot.common.enums.sys.SysOrgStateEnum
     */
    @ApiModelProperty(value = "机构状态")
    private SysOrgStateEnum orgState;

    @ApiModelProperty(value = "机构管理员账号ID")
    private Long adminUserId;

    @ApiModelProperty(value = "机构过期时间")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "机构描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    @TableField(value = "create_user", updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    private Long createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人ID")
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @ApiModelProperty(value = "是否删除  0否(默认) 1是")
    @TableField(fill = FieldFill.UPDATE)
    @TableLogic
    private Integer status;

}
