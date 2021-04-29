package cn.hjljy.fastboot.pojo.member.dto;

import cn.hjljy.fastboot.common.enums.SexEnum;
import cn.hjljy.fastboot.common.enums.member.MemberSourceEnum;
import cn.hjljy.fastboot.pojo.BaseDto;
import cn.hjljy.fastboot.autoconfig.aspect.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@ApiModel(value = "MemberBaseInfoDto对象", description = "会员基础信息")
public class MemberBaseInfoDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员ID")
    @NotNull(message = "会员ID不能为空", groups = Update.class)
    private Long memberId;

    @ApiModelProperty(value = "会员所属机构ID")
    @NotNull(message = "会员所属机构ID不能为空")
    private Long orgId;

    /**
     * @see  cn.hjljy.fastboot.common.enums
     */
    @ApiModelProperty(value = "会员性别 0女 1男 -1保密(默认)")
    private SexEnum memberSex;

    @ApiModelProperty(value = "会员名称")
    @NotBlank(message = "会员名称不能为空")
    private String memberName;

    @ApiModelProperty(value = "会员手机号")
    @NotBlank(message = "会员手机号不能为空")
    private String memberPhone;

    @ApiModelProperty(value = "会员生日：yyyy-MM-dd")
    private String memberBirth;

    @ApiModelProperty(value = "会员等级Id -1非会员(默认)")
    private Long levelId;

    @ApiModelProperty(value = "账户余额")
    private Long balance;

    @ApiModelProperty(value = "赠送余额")
    private Long genBalance;

    @ApiModelProperty(value = "会员卡号")
    private String memberCard;

    /**
     * 具体来源见枚举类
     *
     * @see cn.hjljy.fastboot.common.enums.member.MemberSourceEnum
     */
    @ApiModelProperty(value = "会员来源")
    private MemberSourceEnum source;

    @ApiModelProperty(value = "会员积分")
    private Long memberIntegral;

    @ApiModelProperty(value = "成长值")
    private Integer growthValue;

    @ApiModelProperty(value = "会员备注信息")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createUser;

}
