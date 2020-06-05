package cn.hjljy.fastboot.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yichaofan
 * @date 2020/6/4 16:57
 * @apiNote 前端AJAX请求返回实体
 */
@ApiModel(value = "前端AJAX请求返回实体")
public class AjaxResult {
    @ApiModelProperty(value = "状态码，非0表示失败")
    private int code;

    @ApiModelProperty(value = "请求返回消息")
    private String msg;

    @ApiModelProperty(value = "请求返回数据")
    private Object data;

    @ApiModelProperty(value = "请求返回时间")
    private Long timestamp;


    public AjaxResult() {
    }

    public AjaxResult(Object data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public AjaxResult(int code,String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();
    }

    public static AjaxResult error(String msg) {
        AjaxResult ajaxResult = error(ResultCode.DEFAULT);
        ajaxResult.setMsg(msg);
        return ajaxResult;
    }

    public static AjaxResult error(ResultCode code) {
        return new AjaxResult(code.getCode(),code.getMsg());
    }

    public static AjaxResult success(Object data) {
        return new AjaxResult(data);
    }
    public static AjaxResult success() {
        return new AjaxResult(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg());
    }

    public static AjaxResult error(int code, String message) {
        return new AjaxResult(code, message);
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
