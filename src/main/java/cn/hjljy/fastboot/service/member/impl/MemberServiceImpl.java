package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.autoconfig.exception.BusinessException;
import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import cn.hjljy.fastboot.common.enums.member.OrderTypeEnum;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.pojo.member.dto.MemberDto;
import cn.hjljy.fastboot.pojo.member.dto.RechargeParam;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import cn.hjljy.fastboot.pojo.member.po.MemberOrderInfo;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import cn.hjljy.fastboot.service.member.IMemberMoneyRecordService;
import cn.hjljy.fastboot.service.member.IMemberOrderInfoService;
import cn.hjljy.fastboot.service.member.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 会员服务impl
 *
 * @author hjljy
 * @date 2021/05/17
 */
@Service
@Slf4j
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberBaseInfoService baseInfoService;

    @Autowired
    private IMemberOrderInfoService orderInfoService;

    @Autowired
    private IMemberMoneyRecordService moneyRecordService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberDto recharge(RechargeParam rechargeParam) {
        MemberDto dto = new MemberDto();
        // 1 验证会员是否存在
        MemberBaseInfo baseInfo = baseInfoService.memberExist(rechargeParam.getMemberId());
        if (!baseInfo.getOrgId().equals(SecurityUtils.getOrgId())) {
            throw new BusinessException(ResultCode.DEFAULT);
        }
        // 2 创建支付订单
        MemberOrderInfo order = orderInfoService.createOrder(baseInfo.getMemberId(), rechargeParam.getOrderSource(), rechargeParam.getPayType(), rechargeParam.getMoney(), OrderTypeEnum.NORMAL);
        Long payUuid = SnowFlakeUtil.orderNum();
        // 3 支付订单支付成功
        orderInfoService.success(order.getOrderNum(), rechargeParam.getMoney(), rechargeParam.getPayType(), payUuid);
        // 4 更新会员账号金额
        baseInfo = baseInfoService.updateBalance(baseInfo.getMemberId(), rechargeParam.getMoney(), BigDecimal.ZERO, ConsumeTypeEnum.RECHARGE);
        // 5 更新会员成长值
        baseInfo = this.updateMemberGrowthValueAndLevel(baseInfo.getMemberId(), rechargeParam.getMoney(), ConsumeTypeEnum.RECHARGE, "管理后台-会员充值");
        BeanUtils.copyProperties(baseInfo, dto);
        return dto;
    }

    @Override
    public MemberBaseInfo updateMemberBalance(Long memberId, Long orderNum, BigDecimal money, BigDecimal giftMoney, ConsumeTypeEnum consumeType) {
        // 1 更新会员金额变动情况
        MemberBaseInfo baseInfo = baseInfoService.updateBalance(memberId, money, giftMoney, consumeType);
        // 2 记录会员金额变动情况
        moneyRecordService.saveMoneyRecord(memberId, orderNum, money, giftMoney, consumeType, baseInfo.getBalance(), baseInfo.getGenBalance());
        return baseInfo;
    }


    @Override
    public MemberBaseInfo updateMemberGrowthValueAndLevel(Long memberId, BigDecimal money, ConsumeTypeEnum consumeType, String remark) {
        MemberBaseInfo baseInfo = baseInfoService.memberExist(memberId);
        // 1 更新基础会员成长值
        baseInfoService.updateGrowthValue(baseInfo, money, consumeType, remark);
        // 2 更新会员等级
        baseInfoService.updateMemberLevel(baseInfo, remark);
        return baseInfo;
    }
}
