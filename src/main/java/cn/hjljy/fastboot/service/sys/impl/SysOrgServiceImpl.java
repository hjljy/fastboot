package cn.hjljy.fastboot.service.sys.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.pojo.sys.dto.SysOrgDto;
import cn.hjljy.fastboot.pojo.sys.po.SysOrg;
import cn.hjljy.fastboot.mapper.sys.SysOrgMapper;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.sys.ISysOrgService;
import cn.hjljy.fastboot.service.BaseService;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        SysOrg org = this.getById(orgId);
        list.add(org);
        list.addAll(this.selectList(SysOrg.builder().pid(orgId).build()));
        return list;
    }

    @Override
    public Boolean editOrgBaseInfo(SysOrgDto param) {
        SysOrg org = this.getById(param.getId());
        org.setName(param.getName());
        org.setPid(param.getPid());
        org.setLogo(param.getLogo());
        org.setDescription(param.getDescription());
        return this.updateById(org);
    }

    /**
     * 转换成树形结构
     *
     * @param dtoList
     */
    private List<SysOrgDto> change2TreeList(List<SysOrgDto> dtoList) {
        List<SysOrgDto> list = new ArrayList<>();
        List<SysOrgDto> dtos = dtoList.stream().filter(n -> n.getPid() == 0).collect(Collectors.toList());
        list.addAll(dtos);
        list.forEach(n -> {
            List<SysOrgDto> children = dtoList.stream().filter(m -> m.getPid().equals(n.getId())).collect(Collectors.toList());
            n.setChildren(children);
        });
        return list;
    }
}
