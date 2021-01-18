package cn.hjljy.fastboot.common.result;

/**
 * @author yichaofan
 * @date 2020/6/4 16:57
 * @apiNote 前端AJAX请求返回码以及含义
 */
public enum ResultCode {
    //统一返回
    SUCCESS(0, "操作成功"),
    DEFAULT(1, "操作失败"),
    ERROR(2, "服务器异常,请联系管理员"),


    //业务逻辑异常
    CUSTOM_EXCEPTION(10000,"操作逻辑异常，不允许本次操作"),
    PARAMETERS_EXCEPTION(10001,"请求参数错误"),
    REQUEST_METHOD_EXCEPTION(10002,"请求方式错误"),

    //用户 权限相关异常
    USER_NOT_FOUND_OR_ENABLE(20001,"用户不存在或被禁用"),
    USER_EXIST(20002,"用户已存在"),
    USER_PASSWORD_WRONG(20003,"用户名或密码错误"),

    PERMISSION_DENIED(21001,"权限不足，请联系管理员"),
    TOKEN_EXPIRED(21002,"会话已过期, 请重新登录"),
    TOKEN_NOT_FOUND(21023,"未携带TOKEN,非法请求！"),

    //会员系统异常码
    MEMBER_NOT_FOUND(40001,"未找到对应的会员信息"),
    MEMBER_EXIST(40002,"会员已存在或者会员卡号重复"),
    MEMBER_LEVEL_EXIST(40101,"会员等级已存在或会员名称重复"),
    MEMBER_LEVEL_NOT_EXIST(40102,"会员等级不存在"),
    //服务端代码异常
    SQL_EXCEPTION(90000,"服务器异常,请联系管理员"),
    NPE_EXCEPTION(90001,"服务器异常,请联系管理员");

    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
