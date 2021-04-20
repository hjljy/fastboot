package cn.hjljy.fastboot.autoconfig.exception;

import cn.hjljy.fastboot.common.result.ResultCode;

/**
 * @author yichaofan
 * @date 2020/6/5 17:55
 * @apiNote 自定义异常
 */
public class BusinessException extends RuntimeException {

    private ResultCode code;

    public BusinessException() {
        super(ResultCode.CUSTOM_EXCEPTION.getMsg());
        this.code = ResultCode.CUSTOM_EXCEPTION;
    }

    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.CUSTOM_EXCEPTION;
    }

    public BusinessException(String message, ResultCode code) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResultCode code) {
        super(code.getMsg());
        this.code = code;
    }

    public BusinessException(ResultCode code, String msg) {
        super(msg);
        this.code = code;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }
}
