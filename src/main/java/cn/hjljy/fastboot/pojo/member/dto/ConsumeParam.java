package cn.hjljy.fastboot.pojo.member.dto;

import cn.hjljy.fastboot.common.enums.member.OrderSourceEnum;
import cn.hjljy.fastboot.common.enums.member.PayTypeEnum;
import cn.hjljy.fastboot.pojo.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 消费参数
 *
 * @author hjljy
 * @description: 会员消费参数
 * @date 2021/05/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConsumeParam extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员ID")
    @NotNull(message = "会员ID不能为空")
    private Long memberId;

    @ApiModelProperty(value = "支付方式")
    @NotNull(message = "支付方式")
    private PayTypeEnum payType;

    @ApiModelProperty(value = "金额")
    @NotNull(message = "消费金额不能为空")
    private BigDecimal money;

    @ApiModelProperty(value = "消费订单来源")
    @NotNull(message = "消费订单来源")
    private OrderSourceEnum orderSource;

    @ApiModelProperty(value = "消费备注")
    private String remark;
}
