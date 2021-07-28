package cn.hjljy.fastboot.common.enums;

/**
 * 用户类型枚举
 *
 * @author hjljy
 * @date 2021/07/21
 */
public enum UserTypeEnum {
    /**
     * sys_admin
     */
    SYS_ADMIN,
    ADMIN,
    NORMAL;

    public static Boolean isSysAdmin(String userType){
        return SYS_ADMIN.name().equals(userType);
    }
}
