package cn.hjljy.fastboot.pojo.member.po;

import java.math.BigDecimal;

import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员消费充值记录
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_money_record")
@ApiModel(value="MemberMoneyRecord对象", description="会员消费充值记录")
public class MemberMoneyRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty(value = "记录类型")
    @TableField("type")
    private ConsumeTypeEnum type;

    @ApiModelProperty(value = "订单编号")
    @TableField("order_num")
    private Long orderNum;

    @ApiModelProperty(value = "消费/充值金额")
    @TableField("money")
    private BigDecimal money;

    @ApiModelProperty(value = "消费/充值赠送金额")
    @TableField("give_money")
    private BigDecimal giveMoney;

    @ApiModelProperty(value = "消费/充值后账户充值余额")
    @TableField("balance")
    private BigDecimal balance;

    @ApiModelProperty(value = "消费/充值后账户赠送余额")
    @TableField("gen_balance")
    private BigDecimal genBalance;

    @ApiModelProperty(value = "创建时间")
    @TableField(updateStrategy = FieldStrategy.NEVER,fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否禁用 0否(默认) 1是")
    @TableLogic
    private Boolean status;


}
