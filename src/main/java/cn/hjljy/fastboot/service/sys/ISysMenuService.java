package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
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
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 列表通过用户id
     *
     * @param id id
     * @return {@link List<SysMenu>}
     */
    List<SysMenu> listByUserId(Long id);
}
