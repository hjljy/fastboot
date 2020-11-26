package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.mapper.sys.SysUserMapper;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-09
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUserMapper, SysUser> implements ISysUserService {
    @Override
    public SysUser selectByUserName(String username) {
        SysUser po = new SysUser();
        po.setUserName(username);
        return selectOne(po);
    }
}