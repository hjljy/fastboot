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
 * 会员消费充值记录
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-05-14
 */
@Data
    @EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberMoneyRecordDto对象", description = "会员消费充值记录")
public class MemberMoneyRecordDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;



                @TableId(value = "id", type = IdType.AUTO)
                private Integer id;

        @ApiModelProperty(value = "会员id")
    @TableField("member_id")
        private Long memberId;

        @ApiModelProperty(value = "记录类型")
    @TableField("type")
        private String type;

        @ApiModelProperty(value = "订单编号")
    @TableField("order_num")
        private Long orderNum;

        @ApiModelProperty(value = "消费/充值金额")
    @TableField("money")
        private BigDecimal money;

        @ApiModelProperty(value = "消费/充值赠送金额")
    @TableField("give_money")
        private BigDecimal giveMoney;

        @ApiModelProperty(value = "消费/充值后充值金额")
    @TableField("balance")
        private BigDecimal balance;

        @ApiModelProperty(value = "消费/充值后赠送金额")
    @TableField("gen_balance")
        private BigDecimal genBalance;

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
