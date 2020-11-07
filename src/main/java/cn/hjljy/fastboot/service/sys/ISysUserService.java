package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.po.SysUserPo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-07
 */
public interface ISysUserService extends IService<SysUserPo> {
    /**
     * 根据用户名称查询账号信息
     * @param username
     */
    SysUserPo selectByUserName(String username);
}
