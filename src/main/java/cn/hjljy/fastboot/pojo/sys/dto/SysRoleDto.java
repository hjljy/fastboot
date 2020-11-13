package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.pojo.sys.po.SysRole;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
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
public class SysRoleDto extends SysRole implements Serializable {

    private static final long serialVersionUID=1L;

}
