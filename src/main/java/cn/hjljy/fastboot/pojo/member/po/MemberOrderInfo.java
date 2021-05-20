package cn.hjljy.fastboot.pojo.member.po;

import java.math.BigDecimal;

import cn.hjljy.fastboot.common.enums.member.OrderSourceEnum;
import cn.hjljy.fastboot.common.enums.member.OrderStateEnum;
import cn.hjljy.fastboot.common.enums.member.OrderTypeEnum;
import cn.hjljy.fastboot.common.enums.member.PayTypeEnum;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员充值消费订单表
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_order_info")
@ApiModel(value = "MemberOrderInfo对象", description = "会员充值消费订单表")
public class MemberOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员ID")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty(value = "订单编号")
    @TableField("order_num")
    private Long orderNum;

    @ApiModelProperty(value = "订单状态")
    @TableField("order_state")
    private OrderStateEnum orderState;

    @ApiModelProperty(value = "订单来源")
    @TableField("order_source")
    private OrderSourceEnum orderSource;

    @ApiModelProperty(value = "订单类型")
    @TableField("order_type")
    private OrderTypeEnum orderType;

    @ApiModelProperty(value = "支付方式")
    @TableField("pay_type")
    private PayTypeEnum payType;

    @ApiModelProperty(value = "支付订单号")
    @TableField("pay_uuid")
    private Long payUuid;

    @ApiModelProperty(value = "支付时间")
    @TableField("pay_time")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "实际金额")
    @TableField("money")
    private BigDecimal money;

    @ApiModelProperty(value = "支付金额")
    @TableField("pay_money")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "退款金额")
    @TableField("refund_money")
    private BigDecimal refundMoney;

    @ApiModelProperty(value = "错误说明")
    @TableField("error_msg")
    private String errorMsg;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", updateStrategy = FieldStrategy.NEVER,fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    @TableField(value = "create_user",updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    private Long createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人ID")
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @ApiModelProperty(value = "是否禁用 0否(默认) 1是")
    @TableLogic
    private Boolean status;


}
