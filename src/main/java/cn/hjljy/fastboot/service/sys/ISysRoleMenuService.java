package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.po.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-07-21
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取菜单id通过角色id
     *
     * @param roleIds 角色id
     * @return {@link List<Long>}
     */
    List<Long> getMenuIdsByRoleIds(List<Long> roleIds);
}
