package cn.hjljy.fastboot.pojo.member.dto;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import cn.hjljy.fastboot.pojo.BaseDto;
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
@ApiModel(value = "MemberOrderInfoDto对象", description = "会员充值消费订单表")
public class MemberOrderInfoDto extends BaseDto implements Serializable {

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
        private String orderState;

        @ApiModelProperty(value = "支付方式")
    @TableField("pay_type")
        private Integer payType;

        @ApiModelProperty(value = "支付方式名称")
    @TableField("pay_name")
        private String payName;

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
                @TableField(value = "create_time", fill = FieldFill.INSERT)
        private LocalDateTime createTime;

        @ApiModelProperty(value = "创建人ID")
    @TableField("create_user")
        private Long createUser;

        @ApiModelProperty(value = "更新时间")
                @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
        private LocalDateTime updateTime;

        @ApiModelProperty(value = "更新人ID")
    @TableField("update_user")
        private Long updateUser;

        @ApiModelProperty(value = "是否禁用 0否(默认) 1是")
                @TableField(value = "status", fill = FieldFill.INSERT)
            @TableLogic
private Boolean status;



}
