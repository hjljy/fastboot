package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.po.SysUserRole;
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
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取用户角色id
     *
     * @param userId 用户id
     * @return {@link List<Long>}
     */
    List<Long> getUserRoleIds(Long userId);
}
