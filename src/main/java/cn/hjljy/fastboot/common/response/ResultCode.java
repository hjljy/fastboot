package cn.hjljy.fastboot.common.response;

public enum ResultCode {

    SUCCESS(0, "成功返回码"),
    DEFAULT(-1, "失败返回码"),

    CUSTOM_EXCEPTION(10000,"客户端业务逻辑异常"),
    NPE_EXCEPTION(10001,"NPE空指针异常");

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
