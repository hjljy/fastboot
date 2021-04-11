package cn.hjljy.fastboot.common.enums.sys;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构状态枚举类
 *
 * @author yichaofan
 */

public enum SysOrgStateEnum {
    /**
     * 体验中
     */
    EXPERIENCE,
    /**
     * 正常使用
     */
    USING,
    /**
     * 过期
     */
    EXPIRE,
    /**
     * 禁用
     */
    DISABLE;

    /**
     * 获取未使用的机构状态
     *
     * @return 未使用的机构状态集合
     */
    public static List<String> noUsing() {
        List<String> list = new ArrayList<>();
        list.add(DISABLE.name());
        list.add(EXPIRE.name());
        return list;
    }

    /**
     * 判断机构是否被使用
     * @param orgState 机构状态
     * @return 是否被使用
     */
    public static Boolean isUsing(String orgState) {
        return noUsing().contains(orgState);
    }
}
