package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.common.BaseDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysRoleDto;
import cn.hjljy.fastboot.pojo.sys.po.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-10
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     * 获取用户的角色信息
     * @param userId 用户ID
     */
    List<SysRole> getUserRoleInfo(Long userId);
    /**
     * 获取用户的角色信息
     * @param userIds 用户ID集合
     */
    List<SysRoleDto> getUserRoleInfos(List<Long> userIds);

    /**
     * 查询所有角色信息
     * @param param  查询参数
     * @return List<SysRoleDto> 角色信息集合
     */
    List<SysRoleDto> list(BaseDto param);
}
