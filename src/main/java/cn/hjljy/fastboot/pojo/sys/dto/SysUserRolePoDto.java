package cn.hjljy.fastboot.pojo.sys.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUserRolePoDto对象", description="")
public class SysUserRolePoDto extends SysUserRolePo implements Serializable {

    private static final long serialVersionUID=1L;






}
