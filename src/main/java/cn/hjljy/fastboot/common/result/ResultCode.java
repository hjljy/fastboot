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

    //业务逻辑异常
    CUSTOM_EXCEPTION(10000,"请求逻辑异常"),
    PARAMERS_EXCEPTION(10001,"请求参数异常"),

    //
    USER_NOT_FOUND(20000,"用户不存在"),
    USER_NOT_ENABLE(20001,"用户被禁用"),
    USER_PASSWORD_WRONG(20002,"用户名或密码错误"),
    //服务端代码异常
    SQL_EXCEPTION(90000,"SQL异常"),
    NPE_EXCEPTION(90001,"NPE异常");

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
