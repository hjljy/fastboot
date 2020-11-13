package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-09
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 根据用户账号查询用户信息
     * @param username 用户账号
     * @return
     */
    SysUser selectByUserName(String username);
}
