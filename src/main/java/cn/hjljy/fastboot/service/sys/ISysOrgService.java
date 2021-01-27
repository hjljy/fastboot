package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.dto.SysOrgDto;
import cn.hjljy.fastboot.pojo.sys.po.SysOrg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-01-26
 */
public interface ISysOrgService extends IService<SysOrg> {


    /**
     * 查询当前登录用户能看到的所有机构信息
     * @return 机构列表
     */
    List<SysOrgDto> getOrgListByUser();

    /**
     * 根据用户角色返回用户能看到的所有机构信息
     * @param userId 用户ID
     * @return 机构列表
     */
    List<SysOrg> selectByUserId(Long userId);

    /**
     * 查询机构信息（包含子机构）
     * @param orgId 机构ID
     * @return 机构信息
     */
    List<SysOrg> selectByOrgId(Long orgId);


}
