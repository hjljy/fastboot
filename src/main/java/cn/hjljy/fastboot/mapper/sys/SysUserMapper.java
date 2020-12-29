package cn.hjljy.fastboot.mapper.sys;

import cn.hjljy.fastboot.pojo.sys.dto.SysUserDto;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-09
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询用户信息(未包含用户角色信息)
     * @param page 分页
     * @param orgId 机构ID
     * @param keywords 关键字
     * @param roleId 角色ID
     * @return
     */
    List<SysUserDto> getSysUserInfoPage(Page<SysUserDto> page, @Param("orgId") Long orgId,@Param("keywords") String keywords,@Param("roleId") Long roleId);
}
