package cn.hjljy.fastboot.pojo.member.dto;

import cn.hjljy.fastboot.pojo.BaseDto;
import cn.hjljy.fastboot.autoconfig.aspect.validated.Select;
import cn.hjljy.fastboot.autoconfig.aspect.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberLevelParam对象", description = "会员等级查询参数对象")
public class MemberLevelParam extends BaseDto {

    @ApiModelProperty(value = "机构ID")
    @NotNull(message = "所属机构ID不能为空", groups = {Select.class, Update.class})
    private Long orgId;

    @ApiModelProperty(value = "会员等级ID")
    @NotNull(message = "会员等级ID不能为空", groups = Update.class)
    private Long levelId;

}
