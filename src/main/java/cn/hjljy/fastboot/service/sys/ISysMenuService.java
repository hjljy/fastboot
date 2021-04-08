package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
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
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 获取用户的权限菜单信息
     *
     * @param userId   用户ID
     * @param userType 用户类型
     * @param orgId    机构ID
     * @return 用户菜单权限集合
     */
    List<SysMenu> getUserMenuListInfo(Long userId, String userType, Long orgId);

    /**
     * 获取机构管理员菜单权限集合 （即获取机构菜单权限）
     *
     * @param orgId 机构ID
     * @return 机构菜单权限集合
     */
    List<SysMenu> getOrgMenuListByOrgId(Long orgId);

}
