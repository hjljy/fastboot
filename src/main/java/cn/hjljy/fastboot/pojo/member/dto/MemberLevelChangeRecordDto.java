package cn.hjljy.fastboot.pojo.member.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import cn.hjljy.fastboot.pojo.BaseDto;
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
 * @since 2021-05-20
 */
@Data
    @EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberLevelChangeRecordDto对象", description = "")
public class MemberLevelChangeRecordDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;



                @TableId(value = "id", type = IdType.AUTO)
                private Integer id;

        @ApiModelProperty(value = "会员ID")
    @TableField("member_id")
        private Integer memberId;

        @ApiModelProperty(value = "老会员等级id")
    @TableField("old_level_id")
        private Integer oldLevelId;

        @ApiModelProperty(value = "老会员等级名称")
    @TableField("old_level_name")
        private String oldLevelName;

        @ApiModelProperty(value = "新会员等级id")
    @TableField("new_level_id")
        private Integer newLevelId;

        @ApiModelProperty(value = "新会员等级名称")
    @TableField("new_level_name")
        private String newLevelName;

        @ApiModelProperty(value = "变更时间")
    @TableField("change_time")
        private LocalDateTime changeTime;

        @ApiModelProperty(value = "备注")
    @TableField("remark")
        private String remark;



}
