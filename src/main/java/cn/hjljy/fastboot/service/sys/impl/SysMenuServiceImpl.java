package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hjljy.fastboot.mapper.sys.SysMenuMapper;
import cn.hjljy.fastboot.service.sys.ISysMenuService;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.sys.ISysRoleMenuService;
import cn.hjljy.fastboot.service.sys.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-07-21
 */
@Service
public class SysMenuServiceImpl extends BaseService<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    ISysUserRoleService userRoleService;

    @Autowired
    ISysRoleMenuService roleMenuService;

    @Override
    public List<SysMenu> listByUserId(Long id) {
        List<Long> roleIds = userRoleService.getUserRoleIds(id);
        List<Long> menuIds = roleMenuService.getMenuIdsByRoleIds(roleIds);
        return this.listByIds(menuIds);
    }
}
