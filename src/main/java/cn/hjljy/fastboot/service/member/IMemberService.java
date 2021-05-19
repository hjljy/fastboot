package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.pojo.member.dto.MemberDto;
import cn.hjljy.fastboot.pojo.member.dto.RechargeParam;

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
}
