package cn.hjljy.fastboot.pojo.member.po;

import cn.hjljy.fastboot.common.enums.SexEnum;
import cn.hjljy.fastboot.common.enums.member.MemberSourceEnum;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value = "MemberBaseInfo对象",description = "member_base_info表")
public class MemberBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "会员ID")
    private Long memberId;

    @ApiModelProperty(value = "会员所属机构ID")
    private Long orgId;

    /**
     * @see  cn.hjljy.fastboot.common.enums
     */
    @ApiModelProperty(value = "会员性别 0女 1男 -1保密(默认)")
    private SexEnum memberSex;

    @ApiModelProperty(value = "会员名称")
    private String memberName;

    @ApiModelProperty(value = "会员手机号")
    private String memberPhone;

    @ApiModelProperty(value = "会员生日：yyyy-MM-dd")
    private String memberBirth;

    @ApiModelProperty(value = "会员等级Id 没有会员等级时置为0")
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
    @TableField(updateStrategy = FieldStrategy.NEVER,fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField(updateStrategy = FieldStrategy.NEVER,fill = FieldFill.INSERT)
    private Long createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @ApiModelProperty(value = "是否禁用 0否(默认) 1是")
    @TableLogic
    private Integer status;


}
