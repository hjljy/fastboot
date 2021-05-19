package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import cn.hjljy.fastboot.common.enums.member.OrderTypeEnum;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    IMemberBaseInfoService baseInfoService;

    @Autowired
    IMemberOrderInfoService orderInfoService;

    @Override
    public MemberDto recharge(RechargeParam rechargeParam) {
        // 1 验证会员是否存在
        MemberBaseInfo baseInfo = baseInfoService.memberExist(rechargeParam.getMemberId());
        // 2 创建支付订单
        MemberOrderInfo order = orderInfoService.createOrder(baseInfo.getMemberId(), rechargeParam.getOrderSource(), rechargeParam.getPayType(), rechargeParam.getMoney(), OrderTypeEnum.NORMAL);
        Long payUuid = SnowFlakeUtil.OrderNum();
        // 3 支付订单支付成功
        orderInfoService.success(order.getOrderNum(),rechargeParam.getMoney(),rechargeParam.getPayType(), payUuid);
        // 4 更新会员账号金额
        baseInfoService.updateBalance(baseInfo.getMemberId(),order.getOrderNum(),rechargeParam.getMoney(), BigDecimal.ZERO,ConsumeTypeEnum.RECHARGE);

        return null;
    }
}