package cn.hjljy.fastboot.pojo.sys.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUserPoDto对象", description="")
public class SysUserPoDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户邮箱")
    private String emali;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "用户性别 -1保密(默认) 0女 1男 ")
    private Integer sex;

    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "用户描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否禁用  0否(默认) 1是")
    private Integer enable;

    @ApiModelProperty(value = "是否删除  0否(默认) 1是")
    @TableLogic
    private Integer status;


}
