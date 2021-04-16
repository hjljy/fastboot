package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.constant.Constant;
import cn.hjljy.fastboot.common.constant.RedisPrefixConstant;
import cn.hjljy.fastboot.common.enums.sys.SysOrgStateEnum;
import cn.hjljy.fastboot.autoconfig.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
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
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    RedissonClient redissonClient;

    @Override
    public List<SysOrgDto> getOrgListByUser() {
        List<SysOrgDto> dtoList = new ArrayList<>();
        // 返回当前机构能看到的机构信息
        List<SysOrg> list = this.selectListByOrgId(SecurityUtils.getUserInfo().getOrgId());
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
        List<SysOrg> orgList = getChildrenOrgListByOrgId(orgId);
        list.addAll(orgList);
        return list;
    }

    @Override
    public Boolean editOrgBaseInfo(SysOrgDto param) {
        SysOrg org = this.orgIfExist(param.getId());
        org.setName(param.getName());
        org.setLogo(param.getLogo());
        org.setPhone(param.getPhone());
        org.setAddress(param.getAddress());
        org.setDescription(param.getDescription());
        org.setUpdateTime(LocalDateTime.now());
        org.setExpirationTime(param.getExpirationTime());
        //如果当前改变机构状态，并且将机构状态设置成未使用,
        if (!org.getOrgState().equals(param.getOrgState()) && SysOrgStateEnum.isUsing(param.getOrgState())) {
            //1 清除掉登录token缓存
            redissonClient.getMap(RedisPrefixConstant.ORG + org.getId()).clear();
            //2 停用掉所有子机构
            this.stopChildrenOrg(org.getId());
        }
        //更新机构信息
        return this.updateById(org);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopChildrenOrg(Long pid) {
        LocalDateTime updateTime = LocalDateTime.now();
        List<SysOrg> list = getChildrenOrgListByOrgId(pid);
        for (SysOrg org : list) {
            //1 清除掉登录token缓存
            redissonClient.getMap(RedisPrefixConstant.ORG + org.getId()).clear();
            //2 停用自己
            org.setOrgState(SysOrgStateEnum.DISABLE);
            org.setUpdateTime(updateTime);
        }
        updateBatchById(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOrgByOrgId(Long orgId) {
        if (orgId.equals(SecurityUtils.getOrgId())) {
            throw new BusinessException(ResultCode.DEFAULT,"无法删除自己所属的机构");
        }
        //删除子级机构
        this.deleteChildrenOrg(orgId);
        //删除机构
        //1 清除掉登录token缓存
        redissonClient.getMap(RedisPrefixConstant.ORG + orgId).clear();
        return this.removeById(orgId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChildrenOrg(Long pid) {
        List<SysOrg> list = getChildrenOrgListByOrgId(pid);
        removeByIds(list.stream().map(SysOrg::getId).collect(Collectors.toList()));
    }

    @Override
    public Boolean bindAdmin(Long orgId, Long userId) {
        SysOrg org = orgIfExist(orgId);
        org.setAdminUserId(userId);
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
        List<SysOrg> data =new ArrayList<>();
        SysOrg sysOrg = SysOrg.builder().pid(orgId).status(0).build();
        List<SysOrg> list = this.selectList(sysOrg);
        for (SysOrg org : list) {
            List<SysOrg> children = getChildrenOrgListByOrgId(org.getId());
            data.addAll(children);
        }
        data.addAll(list);
        return data;
    }

    @Override
    public Boolean addOrg(SysOrgDto param) {
        SysOrg org = SysOrg.builder().build();
        BeanUtil.copyProperties(param, org);
        org.setId(SnowFlakeUtil.createId());
        org.setCreateTime(LocalDateTime.now());
        org.setAdminUserId(Constant.LONG_NOT_EXIST);
        return save(org);
    }
}
