package cn.hjljy.fastboot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author hjljy
 * 支付方式
 */
@Getter
public enum PayTypeEnum {
    /**
     * 支付方式
     */
    ALI_PAY(1, "支付宝", "移动支付", "ALI_PAY"),
    WX_PAY(2, "微信支付", "移动支付", "WX_PAY"),
    QQ_PAY(3, "QQ支付", "移动支付", "QQ_PAY"),
    MEMBER_PAY(90, "会员支付", "移动支付", "MEMBER_PAY"),
    CASH_PAY(100, "现金支付", "线下支付", "CASH_PAY");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String remark;

    private final String desc;

    private final String payCode;

    PayTypeEnum(Integer code, String remark, String desc, String payCode) {
        this.code = code;
        this.remark = remark;
        this.desc = desc;
        this.payCode = payCode;
    }
}
