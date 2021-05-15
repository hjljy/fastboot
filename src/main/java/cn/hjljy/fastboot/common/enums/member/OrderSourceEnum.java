package cn.hjljy.fastboot.common.enums.member;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单来源枚举类
 *
 * @author yichaofan
 */
public enum OrderSourceEnum {
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
    public static List<OrderSourceEnum> getSelfList() {
        List<OrderSourceEnum> selfList = new ArrayList<>();
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
    public static Boolean isSelf(OrderSourceEnum sourceEnum){
        return getSelfList().contains(sourceEnum);
    }
}
