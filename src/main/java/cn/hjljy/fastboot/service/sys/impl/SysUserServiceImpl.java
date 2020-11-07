package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.pojo.sys.po.SysUserPo;
import cn.hjljy.fastboot.mapper.sys.SysUserMapper;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserPo> implements ISysUserService {

    @Override
    public SysUserPo selectByUserName(String username) {
        SysUserPo po = new SysUserPo();
        po.setUserName(username);
        QueryWrapper<SysUserPo> queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserPo::getUserName,username);

        return  this.baseMapper.selectOne(queryWrapper);
    }
}
