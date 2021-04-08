package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Select;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PhoneParam对象")
public class PhoneParam extends BaseDto<PhoneParam> {

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "phone", required = true)
    @NotNull(message = "phone不能为空", groups = Select.class)
    @Pattern(regexp = "^1[0-9]{10}$", message = "请输入11位手机号码", groups = Select.class)
    private String phone;

}
