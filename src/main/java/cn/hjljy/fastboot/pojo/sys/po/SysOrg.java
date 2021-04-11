package cn.hjljy.fastboot.pojo.sys.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String orgState;

    @ApiModelProperty(value = "机构管理员账号ID")
    private Long adminUserId;

    @ApiModelProperty(value = "机构过期时间")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "机构描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除  0否(默认) 1是")
    @TableLogic
    private Integer status;

}
