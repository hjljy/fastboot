package cn.hjljy.fastboot.common.enums.member;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 订单状态
 *
 * @author yichoafan
 */
@Getter
public enum OrderStateEnum {
    /**
     * 订单状态
     */
    WAIT_PAY(1, "待支付"),
    COMPLETE_PAY(2, "已完成支付"),
    REFUND_ALL(3, "全部退款"),
    REFUND_PART(4, "部分退款"),
    CANCEL(5, "订单取消"),
    OUT_TIME(6, "订单超时"),
    ERROR(100,"订单异常");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String remark;

    OrderStateEnum(Integer code, String remark) {
        this.code = code;
        this.remark = remark;
    }
}
