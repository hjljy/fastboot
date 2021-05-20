package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import cn.hjljy.fastboot.pojo.member.dto.MemberDto;
import cn.hjljy.fastboot.pojo.member.dto.RechargeParam;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;

import java.math.BigDecimal;

/**
 * 会员服务
 *
 * @author hjljy
 * @date 2021/05/17
 */
public interface IMemberService {

    /**
     * 充值
     *
     * @param rechargeParam 充值参数
     * @return {@link MemberDto}
     */
    MemberDto recharge(RechargeParam rechargeParam);


    /**
     * 更新会员余额
     *
     * @param memberId    会员id
     * @param orderNum    订单num
     * @param money       金额
     * @param giftMoney   赠送金额
     * @param consumeType 消费类型
     * @return {@link MemberBaseInfo}
     */
    MemberBaseInfo updateMemberBalance(Long memberId, Long orderNum, BigDecimal money, BigDecimal giftMoney, ConsumeTypeEnum consumeType);

    /**
     * 更新会员成长价值和水平
     * 更新会员成长价值和等级
     *
     * @param memberId    会员id
     * @param money       金额
     * @param consumeType 消费类型
     * @param remark      备注
     * @return {@link MemberBaseInfo}
     */
    MemberBaseInfo updateMemberGrowthValueAndLevel(Long memberId, BigDecimal money, ConsumeTypeEnum consumeType,String remark);
}
