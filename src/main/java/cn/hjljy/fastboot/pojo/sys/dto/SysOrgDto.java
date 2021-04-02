package cn.hjljy.fastboot.pojo.sys.dto;

import java.time.LocalDateTime;

import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Select;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

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
@ApiModel(value = "SysOrgDto对象", description = "")
public class SysOrgDto extends BaseDto<SysOrgDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户ID不能为空", groups = Select.class)
    @ApiModelProperty(value = "机构ID")
    private Long id;

    @ApiModelProperty(value = "父级机构ID")
    private Long pid;

    @NotNull(message = "机构名称不能为空", groups = Select.class)
    @ApiModelProperty(value = "机构名称")
    private String name;

    @ApiModelProperty(value = "机构地址")
    private String address;

    @ApiModelProperty(value = "机构联系电话")
    private String phone;

    @ApiModelProperty(value = "机构logo")
    private String logo;

    @ApiModelProperty(value = "机构类型 0顶级 1二级")
    private Integer type;
    /**
     * @see cn.hjljy.fastboot.common.enums.sys.SysOrgStateEnum
     */
    @ApiModelProperty(value = "机构状态")
    private String orgState;

    @ApiModelProperty(value = "机构描述")
    private String description;

    @ApiModelProperty(value = "机构管理员账号ID")
    private Long adminUserId;

    @ApiModelProperty(value = "机构管理员账号昵称")
    private String adminNickName;

    @ApiModelProperty(value = "机构管理员账号手机号")
    private String adminPhone;

    @ApiModelProperty(value = "机构排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除  0否(默认) 1是")
    @TableLogic
    private Integer status;

    @ApiModelProperty(value = "子机构")
    private List<SysOrgDto> children;

    @ApiModelProperty(value = "机构菜单权限")
    private List<SysMenuDto> menuList;

}
