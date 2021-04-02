package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.constant.Constant;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.mapper.sys.SysOrgMapper;
import cn.hjljy.fastboot.pojo.sys.dto.SysMenuDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysOrgDto;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hjljy.fastboot.pojo.sys.po.SysOrg;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.sys.ISysMenuService;
import cn.hjljy.fastboot.service.sys.ISysOrgService;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-01-26
 */
@Service
public class SysOrgServiceImpl extends BaseService<SysOrgMapper, SysOrg> implements ISysOrgService {

    @Autowired
    ISysUserService userService;

    @Autowired
    ISysMenuService menuService;

    @Override
    public List<SysOrgDto> getOrgListByUser() {
        List<SysOrgDto> dtoList = new ArrayList<>();
        List<SysOrg> list;
        if (SecurityUtils.IsSuperAdmin()) {
            // 1 超级管理员返回所有机构
            list = this.list();
        } else {
            // 2 其他账号返回能看到的机构信息
            list = this.selectListByOrgId(SecurityUtils.getUserInfo().getOrgId());
        }
        List<Long> userIds = list.stream().map(SysOrg::getAdminUserId).collect(Collectors.toList());
        List<SysUser> users = userService.listByIds(userIds);
        for (SysOrg org : list) {
            SysOrgDto dto = new SysOrgDto();
            BeanUtil.copyProperties(org, dto);
            users.forEach(n -> {
                if (n.getId().equals(org.getAdminUserId())) {
                    dto.setAdminNickName(n.getNickName());
                    dto.setAdminPhone(n.getPhone());
                }
            });
            dtoList.add(dto);
        }
        return dtoList;
    }


    @Override
    public List<SysOrg> selectByUserId(Long userId) {

        return null;
    }

    @Override
    public List<SysOrg> selectListByOrgId(Long orgId) {
        List<SysOrg> list = new ArrayList<>();
        SysOrg org = this.orgIfExist(orgId);
        list.add(org);
        list.addAll(this.selectList(SysOrg.builder().pid(orgId).build()));
        return list;
    }

    @Override
    public Boolean editOrgBaseInfo(SysOrgDto param) {
        SysOrg org = this.orgIfExist(param.getId());
        org.setName(param.getName());
        org.setPid(param.getPid());
        org.setLogo(param.getLogo());
        org.setPhone(param.getPhone());
        org.setAddress(param.getAddress());
        org.setDescription(param.getDescription());
        org.setUpdateTime(LocalDateTime.now());
        return this.updateById(org);
    }

    @Override
    public Boolean deleteOrgByOrgId(Long orgId) {
        //删除机构
        this.removeById(orgId);
        //todo 机构相关的信息是否一并删除
        return true;
    }

    @Override
    public Boolean disableOrg(Long orgId, String orgStatus) {
        SysOrg org = this.orgIfExist(orgId);
        org.setOrgState(orgStatus);
        org.setUpdateTime(LocalDateTime.now());
        return updateById(org);
    }

    @Override
    public SysOrg orgIfExist(Long orgId) {
        SysOrg org = this.getById(orgId);
        if (null == org) {
            throw new BusinessException(ResultCode.ORG_NOT_FOUND);
        }
        return org;
    }

    @Override
    public SysOrgDto getOrgInfoById(Long orgId) {
        SysOrg org = this.orgIfExist(orgId);
        SysOrgDto dto = new SysOrgDto();
        BeanUtil.copyProperties(org, dto);
        //1 判断是否绑定机构管理员账号
        if (!Constant.LONG_NOT_EXIST.equals(dto.getAdminUserId())) {
            Long userId = dto.getAdminUserId();
            SysUser user = userService.getById(userId);
            dto.setAdminPhone(user.getPhone());
            dto.setAdminNickName(user.getNickName());
        }
        //2 获取子机构信息
        List<SysOrg> childrenList = this.getChildrenOrgListByOrgId(orgId);
        List<SysOrgDto> children = new ArrayList<>();
        for (SysOrg sysOrg : childrenList) {
            SysOrgDto child = new SysOrgDto();
            BeanUtil.copyProperties(sysOrg, child);
            children.add(child);
        }
        dto.setChildren(children);
        //3 获取机构的菜单权限信息
        List<SysMenu> menus = menuService.getOrgMenuListByOrgId(orgId);
        List<SysMenuDto> menuList = new ArrayList<>();
        for (SysMenu menu : menus) {
            SysMenuDto menuDto = new SysMenuDto();
            BeanUtil.copyProperties(menu, menuDto);
            menuList.add(menuDto);
        }
        dto.setMenuList(menuList);
        return dto;
    }

    @Override
    public List<SysOrg> getChildrenOrgListByOrgId(Long orgId) {
        SysOrg sysOrg = SysOrg.builder().pid(orgId).status(0).build();
        return this.selectList(sysOrg);
    }
}
