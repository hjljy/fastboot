package cn.hjljy.fastboot.pojo.member.dto;

import java.time.LocalDateTime;

import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Insert;
import cn.hjljy.fastboot.common.aspect.validated.Update;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

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
@ApiModel(value = "MemberLevelDto对象", description = "会员等级详情对象")
public class MemberLevelDto extends BaseDto<MemberLevelDto> {

    @ApiModelProperty(value = "等级ID")
    @NotNull(message = "编辑时等级Id不能为空", groups = {Update.class})
    private Long levelId;

    @ApiModelProperty(value = "机构ID")
    @NotNull(message = "所属机构ID不能为空", groups = {Insert.class, Update.class})
    private Long orgId;

    @ApiModelProperty(value = "等级名称")
    @NotBlank(message = "等级名称不能为空", groups = {Insert.class, Update.class})
    private String levelName;

    @ApiModelProperty(value = "会员等级")
    @NotNull(message = "会员等级不能为空", groups = {Insert.class, Update.class})
    @Min(value = 1, message = "会员等级范围在1-20之间")
    @Max(value = 20, message = "会员等级范围在1-20之间")
    private Integer levelOrder;

    @ApiModelProperty(value = "等级所需成长值")
    @NotNull(message = "成长值不能为空", groups = {Insert.class, Update.class})
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

}
