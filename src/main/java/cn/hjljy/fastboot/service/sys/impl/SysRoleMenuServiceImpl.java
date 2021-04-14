package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.pojo.sys.dto.SysMenuDto;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hjljy.fastboot.pojo.sys.po.SysRoleMenu;
import cn.hjljy.fastboot.mapper.sys.SysRoleMenuMapper;
import cn.hjljy.fastboot.service.sys.ISysRoleMenuService;
import cn.hjljy.fastboot.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-11
 */
@Service
public class SysRoleMenuServiceImpl extends BaseService<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    public Boolean saveRoleMenu(Integer roleId, List<SysMenuDto> menus) {
        //保存角色菜单
        if (null != menus) {
            List<SysMenu> list = SysMenuDto.getList(menus);
            List<SysRoleMenu> roleMenus = new ArrayList<>();
            for (SysMenu sysMenu : list) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(sysMenu.getId());
                roleMenus.add(roleMenu);
            }
            return saveBatch(roleMenus);
        }
        return false;
    }

    @Override
    public void removeByRoleId(Integer roleId) {
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRoleMenu::getRoleId, roleId);
        remove(queryWrapper);
    }
}
