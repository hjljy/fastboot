package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.dto.SysMenuDto;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-11
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     *
     * @param userId 用户ID
     * @return 用户菜单权限集合
     */
    List<SysMenu> getUserMenuListInfo(Long userId);
}