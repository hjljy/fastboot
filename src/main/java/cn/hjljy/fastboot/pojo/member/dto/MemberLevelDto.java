package cn.hjljy.fastboot.pojo.member.dto;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberLevelDto对象", description = "")
public class MemberLevelDto  {

    @ApiModelProperty(value = "等级ID")
    private Long levelId;

    @ApiModelProperty(value = "机构ID")
    @NotNull(message = "所属机构ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "等级名称")
    @NotBlank(message = "等级名称不能为空")
    private String levelName;

    @ApiModelProperty(value = "会员等级排序")
    @NotNull(message = "会员等级排序不能为空")
    private Integer levelOrder;

    @ApiModelProperty(value = "等级所需成长值")
    private Integer upgradeGrowthValue;

    @ApiModelProperty(value = "是否默认新会员等级 0否(默认) 1是")
    private Boolean memberDefault;

    @ApiModelProperty(value = "创建人")
    private Long createUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    private Long updateUser;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否禁用 0否 1是")
    @TableLogic
    private Boolean status;


}
