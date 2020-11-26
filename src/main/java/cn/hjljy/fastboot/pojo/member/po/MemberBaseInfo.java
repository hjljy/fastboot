package cn.hjljy.fastboot.pojo.member.po;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="MemberBaseInfo对象", description="")
public class MemberBaseInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "会员ID")
    private Long memberId;

    @ApiModelProperty(value = "会员所属机构ID")
    private Long orgId;

    @ApiModelProperty(value = "会员性别 0女 1男 -1保密(默认)")
    private Integer memberSex;

    @ApiModelProperty(value = "会员名称")
    private String membrName;

    @ApiModelProperty(value = "会员手机号")
    private String memberPhone;

    @ApiModelProperty(value = "会员生日：年月日")
    private String memberBirth;

    @ApiModelProperty(value = "会员等级Id -1非会员(默认)")
    private Long levelId;

    @ApiModelProperty(value = "账户余额")
    private Long balance;

    @ApiModelProperty(value = "赠送余额")
    private Long genBalance;

    @ApiModelProperty(value = "会员卡号")
    private String memberCard;

    @ApiModelProperty(value = "会员积分")
    @TableField("member_Integral")
    private Long memberIntegral;

    @ApiModelProperty(value = "成长值")
    private Integer growthValue;

    @ApiModelProperty(value = "会员备注信息")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人ID")
    private Long updateUser;

    @ApiModelProperty(value = "是否禁用 0否(默认) 1是")
    @TableLogic
    private Boolean status;


}
