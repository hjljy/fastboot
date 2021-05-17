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
     * 订单请求来源
     */
    WEB,
    H5,
    IOS,
    ANDIRON,
    WINDOWS,
    WEB_SELF,
    H5_SELF,
    IOS_SELF,
    ANDIRON_SELF;

    /**
     * 获取到会员自己创建的订单来源列表
     *
     * @return OrderSourceEnum
     */
    public static List<ConsumeTypeEnum> getSelfList() {
        List<ConsumeTypeEnum> selfList = new ArrayList<>();
        selfList.add(WEB_SELF);
        selfList.add(H5_SELF);
        selfList.add(IOS_SELF);
        selfList.add(ANDIRON_SELF);
        return selfList;
    }

    /**
     * 是否是会员自己创建
     * @param sourceEnum 来源
     * @return 是否
     */
    public static Boolean isSelf(ConsumeTypeEnum sourceEnum){
        return getSelfList().contains(sourceEnum);
    }
}
