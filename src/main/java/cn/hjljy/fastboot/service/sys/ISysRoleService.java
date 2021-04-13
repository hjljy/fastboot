package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.dto.SysRoleDto;
import cn.hjljy.fastboot.pojo.sys.po.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-10
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     * 根据用户ID获取用户的角色信息
     *
     * @param userId   用户ID
     * @param userType 用户类型
     * @param orgId    机构ID
     * @return 用户角色信息
     */
    List<SysRole> getUserRoleInfo(Long userId, String userType, Long orgId);

    /**
     * 获取用户的角色信息
     *
     * @param userIds 用户ID集合
     * @return 用户角色信息
     */
    List<SysRoleDto> getUserRoleInfos(List<Long> userIds);

    /**
     * 查询所有角色信息
     *
     * @param param 查询参数
     * @return List<SysRoleDto> 角色信息集合
     */
    List<SysRoleDto> list(SysRoleDto param);

    /**
     * 查询机构角色信息
     * @param orgId 机构ID
     * @return List<SysRoleDto> 角色信息集合
     */
    List<SysRoleDto> listByOrgId(Long orgId);

    /**
     * 新增角色信息
     * @param param 角色信息
     * @return 是否成功
     */
    Boolean add(SysRoleDto param);

    /**
     * 获取角色详细信息 （包含菜单信息）
     * @param roleId 角色Id
     * @return 角色信息
     */
    SysRoleDto getRoleInfo(Integer roleId);

    /**
     * 编辑角色信息
     * @param param 角色信息
     * @return 是否成功
     */
    Boolean edit(SysRoleDto param);

    /**
     * 删除
     * @param roleId 角色Id
     * @return 是否成功
     */
    Boolean del(Integer roleId);
}
