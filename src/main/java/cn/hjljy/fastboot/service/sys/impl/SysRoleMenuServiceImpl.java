package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.pojo.sys.po.SysRoleMenu;
import cn.hjljy.fastboot.mapper.sys.SysRoleMenuMapper;
import cn.hjljy.fastboot.service.sys.ISysRoleMenuService;
import cn.hjljy.fastboot.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-07-21
 */
@Service
public class SysRoleMenuServiceImpl extends BaseService<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
    @Override
    public List<Long> getMenuIdsByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(SysRoleMenu::getRoleId, roleIds);
        return this.list(queryWrapper).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
    }
}
