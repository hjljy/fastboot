package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.pojo.sys.po.SysRolePo;
import cn.hjljy.fastboot.mapper.sys.SysRoleMapper;
import cn.hjljy.fastboot.service.sys.ISysRoleService;
import cn.hjljy.fastboot.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-10
 */
@Service
public class SysRoleServiceImpl extends BaseService<SysRoleMapper, SysRolePo> implements ISysRoleService {


    @Override
    public void getUserRoleInfo(Long userId) {
//        QueryWrapper<SysRolePo> queryWrapper =new QueryWrapper<>();
//        queryWrapper.lambda().eq(S)
//        this.list(q)
    }
}
