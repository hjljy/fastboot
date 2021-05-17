package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.constant.Constant;
import cn.hjljy.fastboot.common.enums.member.*;
import cn.hjljy.fastboot.common.utils.SnowFlakeUtil;
import cn.hjljy.fastboot.pojo.member.po.MemberOrderInfo;
import cn.hjljy.fastboot.mapper.member.MemberOrderInfoMapper;
import cn.hjljy.fastboot.service.member.IMemberOrderInfoService;
import cn.hjljy.fastboot.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员充值消费订单表 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-05-14
 */
@Service
public class MemberOrderInfoServiceImpl extends BaseService<MemberOrderInfoMapper, MemberOrderInfo> implements IMemberOrderInfoService {

    @Override
    public MemberOrderInfo createOrder(Long memberId, OrderSourceEnum orderSource, PayTypeEnum payType, BigDecimal money, OrderTypeEnum orderType) {
        MemberOrderInfo orderInfo =new MemberOrderInfo();
        orderInfo.setMemberId(memberId);
        orderInfo.setOrderState(OrderStateEnum.WAIT_PAY);
        orderInfo.setOrderSource(orderSource);
        orderInfo.setPayType(payType);
        orderInfo.setOrderType(orderType);
        orderInfo.setMoney(money);
        orderInfo.setPayMoney(BigDecimal.ZERO);
        orderInfo.setRefundMoney(BigDecimal.ZERO);
        orderInfo.setOrderNum(SnowFlakeUtil.OrderNum());
        orderInfo.setPayUuid(Constant.LONG_NOT_EXIST);
        orderInfo.setCreateTime(LocalDateTime.now());
        orderInfo.setUpdateTime(LocalDateTime.now());
        if(OrderSourceEnum.isSelf(orderSource)){
            orderInfo.setCreateUser(Constant.LONG_NOT_EXIST);
            orderInfo.setUpdateUser(Constant.LONG_NOT_EXIST);
        }else {
            orderInfo.setCreateUser(SecurityUtils.getUserId());
            orderInfo.setUpdateUser(SecurityUtils.getUserId());
        }
        this.save(orderInfo);
        return orderInfo;
    }

    @Override
    public void success(Long orderNum, BigDecimal payMoney, PayTypeEnum payType, ConsumeTypeEnum consumeType) {
        MemberOrderInfo orderInfo = this.getByOrderNum(orderNum);
    }

    @Override
    public MemberOrderInfo getByOrderNum(Long orderNum) {
        return this.getOne(new LambdaQueryWrapper<MemberOrderInfo>().eq(MemberOrderInfo::getOrderNum,orderNum));
    }
}
