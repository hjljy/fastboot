package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.pojo.member.dto.MemberLevelDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberLevelParam;
import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
public interface IMemberLevelService extends IService<MemberLevel> {

    /**
     * 获取机构默认的会员等级ID
     *
     * @param orgId 机构ID
     * @return 会员等级信息
     */
    MemberLevel selectOrgDefaultLevelId(Long orgId);

    /**
     * 查询机构全部的会员等级信息
     *
     * @param orgId 机构ID
     * @return 会员等级信息
     */
    List<MemberLevelDto> selectOrgMemberLevelList(Long orgId);

    /**
     * 查询机构会员等级
     *
     * @param orgId      机构ID
     * @param levelOrder 等级排序
     * @return 会员等级信息
     */
    MemberLevel selectOrgLevelByLevelOrder(Long orgId, Integer levelOrder);

    /**
     * 查询机构会员等级
     *
     * @param orgId     机构ID
     * @param levelName 会员等级名称
     * @return 会员等级信息
     */
    MemberLevel selectByOrgIdAndLevelName(Long orgId, String levelName);

    /**
     * 查询机构的最大会员等级
     *
     * @param orgId 机构ID
     * @return 会员等级信息
     */
    MemberLevel selectOrgMaxMemberLevel(Long orgId);

    /**
     * 新增机构会员等级
     *
     * @param dto 会员等级信息
     * @return 结果
     */
    Integer addLevel(MemberLevelDto dto);

    /**
     * 编辑机构会员等级
     *
     * @param dto 会员等级信息
     * @return 1表示成功
     */
    Integer editLevel(MemberLevelDto dto);

    /**
     * 删除会员等级
     *
     * @param param 参数信息
     * @return 1 表示成功
     */
    Boolean delLevel(MemberLevelParam param);

    /**
     * 重置机构的默认会员等级
     *
     * @param userId 操作人ID
     * @param orgId  机构ID
     */
    void resetDefaultLevel(Long userId, Long orgId);
}
