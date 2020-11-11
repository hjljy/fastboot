package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.pojo.sys.po.SysRolePo;
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
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysRolePoDto对象", description="")
public class SysRolePoDto extends SysRolePo implements Serializable {

    private static final long serialVersionUID=1L;

}
