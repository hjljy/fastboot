package cn.hjljy.fastboot.common.exception;

import cn.hjljy.fastboot.common.result.ResultCode;

/**
 * @author yichaofan
 * @date 2020/6/5 17:55
 * @apiNote 自定义异常
 */
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException() {
        super(ResultCode.CUSTOM_EXCEPTION.getMsg());
        this.code = ResultCode.CUSTOM_EXCEPTION.getCode();
    }

    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.CUSTOM_EXCEPTION.getCode();
    }

    public BusinessException(String message, int code) {
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
