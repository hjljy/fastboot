package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.pojo.sys.dto.SysOrgDto;
import cn.hjljy.fastboot.pojo.sys.po.SysOrg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-01-26
 */
public interface ISysOrgService extends IService<SysOrg> {


    /**
     * 查询当前登录用户能看到的所有机构信息
     *
     * @return 机构列表
     */
    List<SysOrgDto> getOrgListByUser();

    /**
     * 根据用户角色返回用户能看到的所有机构信息
     *
     * @param userId 用户ID
     * @return 机构列表
     */
    List<SysOrg> selectByUserId(Long userId);

    /**
     * 查询机构信息（包含子机构）
     *
     * @param orgId 机构ID
     * @return 机构信息
     */
    List<SysOrg> selectListByOrgId(Long orgId);

    /**
     * 编辑机构基础信息
     *
     * @param param 基础信息
     * @return 是否成功
     */
    Boolean editOrgBaseInfo(SysOrgDto param);

    /**
     * 删除机构信息
     *
     * @param orgId 机构ID
     * @return 是否成功
     */
    Boolean deleteOrgByOrgId(Long orgId);


    /**
     * 根据ID获取机构基础信息 判断机构是否存在
     *
     * @param orgId 机构ID
     * @return 机构信息  没有返回机构不存在异常    如需返回null  请使用getById方法
     */
    SysOrg orgIfExist(Long orgId);

    /**
     * 根据ID获取机构详细信息
     *
     * @param orgId 机构ID
     * @return 机构信息  没有返回机构不存在异常
     */
    SysOrgDto getOrgInfoById(Long orgId);

    /**
     * 获取机构的子机构数据（不包含孙子机构）
     *
     * @param orgId 机构ID
     * @return 子机构信息
     */
    List<SysOrg> getChildrenOrgListByOrgId(Long orgId);

    /**
     * 新增机构
     * @param param 新增机构信息
     * @return 是否成功
     */
    Boolean addOrg(SysOrgDto param);

    /**
     * 停用掉所有子级机构
     * @param pid 父级机构ID
     */
    void stopChildrenOrg(Long pid);

    /**
     * 删除子级机构
     * @param pid 父级机构ID
     */
    void deleteChildrenOrg(Long pid);

    /**
     * 机构绑定管理员
     * @param orgId 机构ID
     * @param userId 管理员账号Id
     * @return 是否成功
     */
    Boolean bindAdmin(Long orgId, Long userId);
}
