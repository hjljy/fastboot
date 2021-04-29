package cn.hjljy.fastboot.pojo.member.dto;

import cn.hjljy.fastboot.common.enums.PayTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hjljy
 * @description: 会员充值参数
 */
@Data
public class RechargeParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员ID")
    @NotNull(message = "会员ID不能为空")
    private Long memberId;

    @ApiModelProperty(value = "会员ID")
    @NotNull(message = "支付方式")
    private PayTypeEnum payType;


    @ApiModelProperty(value = "充值金额")
    @NotNull(message = "充值金额不能为空")
    private BigDecimal money;
}
