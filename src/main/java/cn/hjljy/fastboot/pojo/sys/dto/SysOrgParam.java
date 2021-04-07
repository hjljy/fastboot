package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.common.aspect.validated.Select;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysOrgParam对象", description = "")
public class SysOrgParam extends BaseDto<SysOrgParam> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户ID不能为空", groups = Select.class)
    @ApiModelProperty(value = "机构ID")
    private Long id;

    /**
     * @see cn.hjljy.fastboot.common.enums.sys.SysOrgStateEnum
     */
    @ApiModelProperty(value = "机构状态 EXPERIENCE 体验中。USING 使用中。EXPIRE 过期 ，DISABLE 禁用 ")
    private String orgState;
}