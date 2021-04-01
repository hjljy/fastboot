package cn.hjljy.fastboot.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yichaofan
 * @date 2020/6/4 16:57
 * @apiNote 前端AJAX请求返回实体
 */
@ApiModel(value = "前端AJAX请求返回实体")
public class ResultInfo<T> {
    @ApiModelProperty(value = "状态码，非0表示失败")
    private int code;

    @ApiModelProperty(value = "请求返回消息")
    private String msg;

    @ApiModelProperty(value = "请求返回数据")
    private T data;


    public ResultInfo() {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
    }

    public ResultInfo(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    public ResultInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultInfo<T> error(String msg) {
        ResultInfo<T> resultInfo = error(ResultCode.ERROR);
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public ResultInfo<T> error(ResultCode code) {
        return new ResultInfo<>(code.getCode(), code.getMsg());
    }

    public ResultInfo<T> success(T data) {
        return new ResultInfo<>(data);
    }

    public static ResultInfo<Object> error(int code, String message) {
        return new ResultInfo<>(code, message);
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

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
