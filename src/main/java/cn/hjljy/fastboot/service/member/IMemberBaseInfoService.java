package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoParam;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
public interface IMemberBaseInfoService extends IService<MemberBaseInfo> {

    /**
     * 分页查询数据
     *
     * @param param 查询信息
     * @return 会员列表信息
     */
    IPage<MemberBaseInfoDto> getMemberBaseInfoPageList(MemberBaseInfoParam param);

    /**
     * 新增会员信息
     *
     * @param dto 会员信息
     * @return Boolean 结果
     */
    Boolean addMember(MemberBaseInfoDto dto);

    /**
     * 根据会员ID删除会员信息
     *
     * @param memberId 会员ID
     * @return 是否成功
     */
    boolean delMember(Long memberId);

    /**
     * 编辑会员信息
     *
     * @param dto 会员信息
     * @return 是否成功
     */
    Boolean editMember(MemberBaseInfoDto dto);

    /**
     * 根据机构Id和手机号获取会员信息
     *
     * @param memberPhone 会员手机号
     * @param orgId       所属机构ID
     * @return MemberBaseInfo 会员信息
     */
    MemberBaseInfo selectByPhoneAndOrgId(String memberPhone, Long orgId);

    /**
     * 根据机构Id和会员卡号获取会员信息
     *
     * @param memberCard 会员卡号
     * @param orgId      所属机构ID
     * @return MemberBaseInfo 会员信息
     */
    MemberBaseInfo selectByCardAndOrgId(String memberCard, Long orgId);

    /**
     * 判断会员是否存在
     *
     * @param memberId 会员ID
     * @return 会员信息 不存在抛出异常
     */
    MemberBaseInfo memberExist(Long memberId);


    /**
     * 更新余额
     *
     * @param memberId    会员id
     * @param money       金额
     * @param giftMoney   赠送金额
     * @param consumeType 消费类型
     * @return {@link MemberBaseInfo}
     */
    MemberBaseInfo updateBalance(Long memberId, BigDecimal money, BigDecimal giftMoney, ConsumeTypeEnum consumeType);


    /**
     * 更新成长价值
     *
     * @param memberBaseInfo 会员基础信息
     * @param money          金额
     * @param consumeType    消费类型
     * @return {@link Integer}
     */
    Integer updateGrowthValue(MemberBaseInfo memberBaseInfo, BigDecimal money, ConsumeTypeEnum consumeType);

    /**
     * 更新会员水平
     *
     * @param baseInfo 基础信息
     */
    void updateMemberLevel(MemberBaseInfo baseInfo);
}
