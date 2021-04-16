package cn.hjljy.fastboot.pojo.member.dto;

import cn.hjljy.fastboot.pojo.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员权益成长值获取扣减计算规则表
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberUpvDto对象", description = "会员权益成长值获取扣减计算规则表")
public class MemberUpvDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构Id")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "金额")
    private Long money;

    @ApiModelProperty(value = "获取成长值")
    private Long growthValue;

    @ApiModelProperty(value = "普通消费(非会员储值消费)获取")
    private Integer normalConsume;

    @ApiModelProperty(value = "会员储值消费获取")
    private Integer storedConsume;

    @ApiModelProperty(value = "会员充值获取")
    private Integer memberRecharge;

    @ApiModelProperty(value = "退费扣减")
    private Integer refund;

    @ApiModelProperty(value = "计算规则")
    private String countRule;

    @ApiModelProperty(value = "创建人")
    private Long createUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    private Long updateUser;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

}
