package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberListDto;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
public interface IMemberBaseInfoService extends IService<MemberBaseInfo> {

    /**
     * 分页查询数据
     * @param orgId  机构ID
     * @param keywords  关键字
     * @param levelId  等级ID
     * @param pageNo 页码
     * @param pageNum 页数
     * @return 会员列表信息
     */
    IPage<MemberBaseInfoDto> getMemberBaseInfoPageList(Long orgId, String keywords, Long levelId, Integer pageNo, Integer pageNum);

    /**
     * 新增会员信息
     * @param dto 会员信息
     * @return int 结果
     */
    int addMember(MemberBaseInfoDto dto);

    /**
     * 根据机构Id和手机号获取会员信息
     * @param memberPhone 会员手机号
     * @param orgId 所属机构ID
     * @return MemberBaseInfo 会员信息
     */
    MemberBaseInfo selectByPhoneAndOrgId(String memberPhone, Long orgId);

    /**
     * 根据机构Id和会员卡号获取会员信息
     * @param memberCard 会员卡号
     * @param orgId 所属机构ID
     * @return MemberBaseInfo 会员信息
     */
    MemberBaseInfo selectByCardAndOrgId(String memberCard, Long orgId);
}
