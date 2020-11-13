package cn.hjljy.fastboot.mapper.sys;

import cn.hjljy.fastboot.pojo.sys.dto.SysRoleDto;
import cn.hjljy.fastboot.pojo.sys.po.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-10
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 查询用户角色信息
     * @param userId 用户ID
     * @return 返回用户角色信息
     */
    List<SysRole> selectUserRoleInfoByUserId(@Param("userId") Long userId);
}
