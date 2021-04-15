package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.common.enums.sys.SysUserTypeEnum;
import cn.hjljy.fastboot.mapper.sys.SysMenuMapper;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.sys.ISysMenuService;
import org.springframework.stereotype.Service;

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
public class SysMenuServiceImpl extends BaseService<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenu> getUserMenuListInfo(Long userId, SysUserTypeEnum userType, Long orgId) {
        if (SysUserTypeEnum.SUPER_ADMIN.equals(userType)) {
            return this.list();
        } else if (SysUserTypeEnum.SYS_ADMIN.equals(userType) || SysUserTypeEnum.ADMIN.equals(userType)) {
            return this.getOrgMenuListByOrgId(orgId);
        }
        return baseMapper.getUserMenuListInfo(userId);
    }

    @Override
    public List<SysMenu> getOrgMenuListByOrgId(Long orgId) {
        return baseMapper.getOrgMenuListByOrgId(orgId);
    }

    @Override
    public List<SysMenu> getRoleMenuList(Integer roleId) {
        return baseMapper.getRoleMenuList(roleId);
    }
}
