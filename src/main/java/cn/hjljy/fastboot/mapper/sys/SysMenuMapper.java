package cn.hjljy.fastboot.mapper.sys;

import cn.hjljy.fastboot.pojo.sys.dto.SysMenuDto;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-11
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 查询用户菜单集合
     * @param userId 用户ID
     * @return 用户菜单集合
     */
    List<SysMenu> getUserMenuListInfo(@Param("userId") Long userId);
}
