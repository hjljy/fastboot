package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.pojo.sys.po.SysUserRole;
import cn.hjljy.fastboot.mapper.sys.SysUserRoleMapper;
import cn.hjljy.fastboot.service.sys.ISysUserRoleService;
import cn.hjljy.fastboot.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-07-21
 */
@Service
public class SysUserRoleServiceImpl extends BaseService<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Override
    public List<Long> getUserRoleIds(Long userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRole::getUserId, userId);
        return this.list(queryWrapper).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
    }
}
