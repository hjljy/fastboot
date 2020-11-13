package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.pojo.sys.dto.SysMenuDto;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hjljy.fastboot.mapper.sys.SysMenuMapper;
import cn.hjljy.fastboot.service.sys.ISysMenuService;
import cn.hjljy.fastboot.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-11
 */
@Service
public class SysMenuServiceImpl extends BaseService<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Override
    public List<SysMenu> getUserMenuListInfo(Long userId) {
        return baseMapper.getUserMenuListInfo(userId);
    }
}
