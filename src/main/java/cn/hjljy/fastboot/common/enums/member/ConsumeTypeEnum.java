package cn.hjljy.fastboot.common.enums.member;

/**
 * 消费类型枚举
 *
 * @author yichaofan
 * @date 2021/05/16
 */

public enum ConsumeTypeEnum {
    /**
     * 充值
     */
    RECHARGE,
    /**
     * 普通消费（非会员余额消费）
     */
    NORMAL_CONSUME,
    /**
     * 会员余额消费
     */
    BALANCE_CONSUME,
    /**
     * 退费
     */
    REFUND;

    public static boolean isRecharge(ConsumeTypeEnum type) {
        return RECHARGE == type;
    }

    public static boolean isNormalConsume(ConsumeTypeEnum type) {
        return NORMAL_CONSUME == type;
    }

    public static boolean isBalanceConsume(ConsumeTypeEnum type) {
        return BALANCE_CONSUME == type;
    }
    public static boolean isRefund(ConsumeTypeEnum type) {
        return REFUND == type;
    }
}
