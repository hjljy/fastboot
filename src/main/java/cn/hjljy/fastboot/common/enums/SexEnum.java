package cn.hjljy.fastboot.common.enums;

/**
 * 性别枚举
 * @author hjljy
 */
public enum SexEnum {
    /**
     * 性别枚举
     */
    DEFAULT(-1, "保密"),
    WOMAN(0, "女"),
    MAN(1, "男");

    private Integer code;

    private String remark;

    SexEnum(Integer code, String remark) {
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
