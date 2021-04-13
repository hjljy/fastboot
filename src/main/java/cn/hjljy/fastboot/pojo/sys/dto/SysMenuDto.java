package cn.hjljy.fastboot.pojo.sys.dto;

import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.pojo.sys.po.SysMenu;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysMenuPoDto对象")
public class SysMenuDto extends BaseDto<SysMenuDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "父级菜单ID")
    private Integer pid;

    @ApiModelProperty(value = "菜单类型 M菜单 P页面 F功能按钮")
    private String menuType;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单权限")
    private String perms;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    /**
     * @see cn.hjljy.fastboot.common.enums.sys
     */
    @ApiModelProperty(value = "菜单渠道 1 web 2 app 3 api")
    private Integer menuChannel;

    @ApiModelProperty(value = "菜单路径")
    private String menuPath;

    @ApiModelProperty(value = "是否可见 0是(默认) 1否")
    private Integer visiable;

    @ApiModelProperty(value = "子菜单")
    private List<SysMenuDto> children;

    /**
     * 将list转换成树形菜单结构
     * @param list 菜单集合
     * @return 树形菜单结构
     */
    public static List<SysMenuDto> getTree(List<SysMenu> list){
        List<SysMenuDto> dtoList =new ArrayList<>();
        List<SysMenu> menus = list.stream().filter(n -> 0 == n.getId()).collect(Collectors.toList());
        for (SysMenu menu : menus) {
            SysMenuDto dto = new SysMenuDto();
            BeanUtil.copyProperties(menu,dto);
            dto.setChildren(getChildrenList(dto.getId(),list));
            dtoList.add(dto);
        }
        return dtoList;
    }

    private static List<SysMenuDto> getChildrenList(Integer id, List<SysMenu> list) {
        List<SysMenuDto> children = new ArrayList<>();
        List<SysMenu> menus = list.stream().filter(n -> n.getPid().equals(id)).collect(Collectors.toList());
        for (SysMenu menu : menus) {
            SysMenuDto dto = new SysMenuDto();
            BeanUtil.copyProperties(menu,dto);
            dto.setChildren(getChildrenList(dto.getId(),list));
            children.add(dto);
        }
        return children;
    }

    /**
     * 将tree菜单转换成list
     * @param tree tree菜单
     * @return list
     */
    public static List<SysMenu> getList(List<SysMenuDto> tree){
        List<SysMenu> menus=new ArrayList<>();
        for (SysMenuDto menuDto : tree) {
            SysMenu menu =new SysMenu();
            BeanUtil.copyProperties(menuDto,menu);
            menus.add(menu);
            List<SysMenuDto> children = menuDto.getChildren();
            menus.addAll(getList(children));
        }
        return menus;
    }

}
