package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.mapper.sys.SysUserMapper;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import cn.hjljy.fastboot.service.BaseService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-06-24
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getByUsername(String username) {
        SysUser user =new SysUser();
        user.setUserName(username);
        return this.selectOne(user);
    }

    @Override
    public Boolean addUser(SysUser sysUser) {
        sysUser.setId(SnowFlakeUtil.createId());
        sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        sysUser.setEnable(false);
        return this.save(sysUser);
    }

    @Override
    public Boolean updateUser(SysUser sysUser) {
        sysUser.setPassword(null);
        return this.updateById(sysUser);
    }
}
