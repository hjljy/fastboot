package cn.hjljy.fastboot.common.exception;

import cn.hjljy.fastboot.common.result.ResultCode;

/**
 * @author yichaofan
 * @date 2020/6/5 17:55
 * @apiNote 自定义异常
 */
public class CustomException extends RuntimeException {

    private int code;

    public CustomException() {
        super(ResultCode.CUSTOM_EXCEPTION.getMsg());
        this.code = ResultCode.CUSTOM_EXCEPTION.getCode();
    }

    public CustomException(String message) {
        super(message);
        this.code = ResultCode.CUSTOM_EXCEPTION.getCode();
    }

    public CustomException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
