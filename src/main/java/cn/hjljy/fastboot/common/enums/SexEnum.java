package cn.hjljy.fastboot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author hjljy
 */
@Getter
public enum SexEnum {
    /**
     * 性别枚举
     */
    DEFAULT(-1, "保密"),
    WOMAN(0, "女"),
    MAN(1, "男");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String remark;

    SexEnum(Integer code, String remark) {
        this.code = code;
        this.remark = remark;
    }

}
