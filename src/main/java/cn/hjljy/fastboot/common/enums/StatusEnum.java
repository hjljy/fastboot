package cn.hjljy.fastboot.common.enums;

/**
 * 状态枚举
 * @author hjljy
 */
public enum StatusEnum {
    /**
     * 状态枚举
     */
    ENABLE(0, "启用"),
    DISABLE(1, "禁用");

    private Integer code;

    private String remark;

    StatusEnum(Integer code, String remark) {
        this.code = code;
        this.remark = remark;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
