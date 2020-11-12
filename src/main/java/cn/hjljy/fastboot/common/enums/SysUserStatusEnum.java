package cn.hjljy.fastboot.common.enums;

/**
 * 张宏
 */
public enum SysUserStatusEnum {
    ENABLE(0, "启用"),
    DISABLE(1, "禁用");

    private Integer code;

    private String remark;

    SysUserStatusEnum(Integer code, String remark) {
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
