package cn.hjljy.fastboot.common.enums.member;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 支付类型的枚举
 *
 * @author hjljy
 * @date 2021/05/16
 */
@Getter
public enum PayTypeEnum {
    /**
     * 阿里支付
     */
    ALI_PAY(1, "支付宝", "移动支付", "ALI_PAY"),
    /**
     * wx支付
     */
    WX_PAY(2, "微信支付", "移动支付", "WX_PAY"),
    /**
     * 银联
     */
    UNION_PAY(20, "云闪付", "移动支付", "UNION_PAY"),
    /**
     * 成员支付
     */
    MEMBER_PAY(90, "会员支付", "移动支付", "MEMBER_PAY"),
    /**
     * 现金支付
     */
    CASH_PAY(100, "现金支付", "线下支付", "CASH_PAY");

    /**
     * 代码
     */
    @EnumValue
    @JsonValue
    private final Integer code;

    /**
     * 备注
     */
    private final String remark;

    /**
     * 描述
     */
    private final String description;

    /**
     * 支付代码
     */
    private final String payCode;

    /**
     * 支付类型的枚举
     *
     * @param code    代码
     * @param remark  备注
     * @param desc    desc
     * @param payCode 支付代码
     */
    PayTypeEnum(Integer code, String remark, String desc, String payCode) {
        this.code = code;
        this.remark = remark;
        this.description = desc;
        this.payCode = payCode;
    }
}
