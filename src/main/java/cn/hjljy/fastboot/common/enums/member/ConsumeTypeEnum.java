package cn.hjljy.fastboot.common.enums.member;

import java.util.ArrayList;
import java.util.List;

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

    public static Boolean isRecharge(ConsumeTypeEnum type) {
        return RECHARGE == type;
    }

    public static Boolean isNormalConsume(ConsumeTypeEnum type) {
        return NORMAL_CONSUME == type;
    }

    public static Boolean isBalanceConsume(ConsumeTypeEnum type) {
        return BALANCE_CONSUME == type;
    }
    public static Boolean isRefund(ConsumeTypeEnum type) {
        return REFUND == type;
    }

    /**
     * 获取扣除项
     * @return 扣除项
     */
    public static List<ConsumeTypeEnum> getDeductList(){
        List<ConsumeTypeEnum> list = new ArrayList<>();
        list.add(ConsumeTypeEnum.REFUND);
        return list;
    }

    /**
     * 是否扣除
     * @return 扣除项
     */
    public static Boolean isDeduct(ConsumeTypeEnum type){
        return getDeductList().contains(type);
    }

}
