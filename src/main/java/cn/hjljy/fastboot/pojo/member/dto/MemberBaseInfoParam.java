package cn.hjljy.fastboot.pojo.member.dto;

import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author yichaofan
 * @apiNote 会员list接口实体
 * @since 2020/11/26 18:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MemberBaseInfoParam extends BaseDto<MemberBaseInfoDto> {

    @ApiModelProperty(value = "会员等级Id 0表示全部")
    private Long levelId;

    @ApiModelProperty(value = "会员所属机构ID")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "会员ID")
    @NotNull(message = "机构ID不能为空", groups = Update.class)
    private Long memberId;
}
