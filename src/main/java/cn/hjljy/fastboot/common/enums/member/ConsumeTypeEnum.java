package cn.hjljy.fastboot.common.enums.member;

import lombok.Getter;

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
     * 消费
     */
    CONSUME;

    public static boolean isRecharge(ConsumeTypeEnum type) {
        return RECHARGE == type;
    }
}
