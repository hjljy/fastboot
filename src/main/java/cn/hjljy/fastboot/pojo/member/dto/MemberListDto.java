package cn.hjljy.fastboot.pojo.member.dto;

import cn.hjljy.fastboot.common.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yichaofan
 * @apiNote 会员list接口实体
 * @since 2020/11/26 18:18
 */
@Data
public class MemberListDto extends BaseDto {

    @ApiModelProperty(value = "会员等级Id 0表示全部")
    private Long levelId;

    @ApiModelProperty(value = "姓名，卡号，手机号")
    private String keywords;
}
