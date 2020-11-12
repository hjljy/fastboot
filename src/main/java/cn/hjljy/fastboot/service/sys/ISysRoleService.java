package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.po.SysRolePo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-10
 */
public interface ISysRoleService extends IService<SysRolePo> {
    /**
     * 获取用户的角色和权限信息
     * @param userId
     */
    void getUserRoleInfo(Long userId);
}
