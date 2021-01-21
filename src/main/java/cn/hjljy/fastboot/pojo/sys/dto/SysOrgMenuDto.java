package cn.hjljy.fastboot.pojo.sys.dto;

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
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysOrgMenuDto对象", description="")
public class SysOrgMenuDto implements Serializable {

    private static final long serialVersionUID=1L;



                @TableId(value = "id", type = IdType.AUTO)
                private Integer id;

        @ApiModelProperty(value = "机构ID")
        private Integer orgId;

        @ApiModelProperty(value = "菜单ID")
        private Integer menuId;

        @ApiModelProperty(value = "状态")
            @TableLogic
private Integer status;



}
