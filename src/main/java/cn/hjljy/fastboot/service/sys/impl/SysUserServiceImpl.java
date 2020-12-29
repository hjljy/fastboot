package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.pojo.sys.dto.SysRoleDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserParam;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.mapper.sys.SysUserMapper;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.sys.ISysRoleService;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-09
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    ISysRoleService roleService;

    @Override
    public SysUser selectByUserName(String username) {
        SysUser po = new SysUser();
        po.setUserName(username);
        return selectOne(po);
    }

    @Override
    public IPage<SysUserDto> getSysUserInfoPage(SysUserParam param) {
        Page<SysUserDto> page = param.createPage();
        List<SysUserDto> infoPage = this.baseMapper.getSysUserInfoPage(page, param.getOrgId(), param.getKeywords(), param.getRoleId());
        if (!CollectionUtils.isEmpty(infoPage)) {
            List<Long> userIds = infoPage.stream().map(SysUserDto::getId).collect(Collectors.toList());
            List<SysRoleDto> roles = roleService.getUserRoleInfos(userIds);
        }
        page.setRecords(infoPage);
        return page;
    }
}
