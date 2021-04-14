package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.dto.SysMenuDto;
import cn.hjljy.fastboot.pojo.sys.po.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-11
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 保存角色菜单
     * @param roleId 角色ID
     * @param menus 菜单信息
     * @return
     */
    Boolean saveRoleMenu(Integer roleId, List<SysMenuDto> menus);

    /**
     * 删除角色菜单
     * @param roleId 角色ID
     */
    void removeByRoleId(Integer roleId);
}
