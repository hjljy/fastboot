package cn.hjljy.fastboot.service.sys;

import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.pojo.sys.dto.PasswordParam;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserDto;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserParam;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-09
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 根据用户账号查询用户信息
     *
     * @param username 用户账号
     * @return SysUser 用户信息
     */
    SysUser selectByUserName(String username);

    /**
     * 分页查询用户信息
     *
     * @param param 分页查询信息
     * @return IPage<SysUserDto> 分页信息
     */
    IPage<SysUserDto> getSysUserInfoPage(SysUserParam param);

    /**
     * 根据Id查询用户所有信息
     *
     * @param userId 用户ID
     * @return 用户详情
     */
    SysUserDto getUserDetailInfoByUserId(Long userId);

    /**
     * 新增用户信息
     *
     * @param dto 用户基础信息
     */
    void addSysUserInfo(SysUserDto dto);

    /**
     * 更新用户信息
     *
     * @param param 用户基础信息
     */
    void updateSysUserInfo(SysUserDto param);

    /**
     * 禁用用户
     *
     * @param param 用户信息
     */
    void disableSysUser(SysUserParam param);

    /**
     * 查询用户基础信息，判断是否存在，没有则抛出异常,有就返回用户信息
     *
     * @param userId 用户ID
     * @return SysUser 用户信息
     * @throws BusinessException 异常码 code = USER_NOT_FOUND
     */
    SysUser userIfExist(Long userId) throws BusinessException;

    /**
     * 逻辑删除用户信息
     *
     * @param userId 删除用户
     */
    void removeSysUser(Long userId);

    /**
     * 重置密码 重置为123456
     *
     * @param userId 用户ID
     */
    void resetUserPassword(Long userId);

    /**
     * 修改账号密码
     *
     * @param param 密码参数
     */
    void editUserPassword(PasswordParam param);

    /**
     * 更新用户账号密码
     *
     * @param sysUser 用户信息
     */
    void updateUserPassword(SysUser sysUser);

    /**
     * 当前用户绑定手机号码
     *
     * @param phone 手机号码
     */
    void bindPhone(String phone);

    /**
     * 根据手机号查询用户基础信息
     *
     * @param phone 手机号码
     * @return sysUser 用户信息集合
     */
    List<SysUser> selectByPhone(String phone);
}
