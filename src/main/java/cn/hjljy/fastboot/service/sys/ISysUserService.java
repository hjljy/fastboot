package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.pojo.sys.vo.SysUserVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-06-24
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 获取通过用户名
     *
     * @param username 用户名
     * @return {@link SysUser}
     */
    SysUser getByUsername(String username);

    /**
     * 添加用户
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    Boolean addUser(SysUser sysUser);

    /**
     * 更新用户
     *
     * @param sysUser 系统用户
     * @return {@link Boolean}
     */
    Boolean updateUser(SysUser sysUser);

    /**
     * 获取用户细节通过id
     *
     * @param userId 用户id
     * @return {@link SysUserVo}
     */
    SysUserVo getUserDetailsById(Long userId);
}
