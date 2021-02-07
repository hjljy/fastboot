package cn.hjljy.fastboot.pojo.member.dto;

import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Select;
import cn.hjljy.fastboot.common.aspect.validated.Update;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
@ApiModel(value = "MemberLevelDto对象", description = "")
public class MemberLevelParam extends BaseDto {

    @ApiModelProperty(value = "机构ID")
    @NotNull(message = "所属机构ID不能为空",groups = {Select.class,Update.class})
    private Long orgId;

    @ApiModelProperty(value = "会员等级ID")
    @NotNull(message = "会员等级ID不能为空",groups = Update.class)
    private Long levelId;

}
