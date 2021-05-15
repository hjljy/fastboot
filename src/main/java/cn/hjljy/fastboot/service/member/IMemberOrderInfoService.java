package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.common.enums.member.OrderSourceEnum;
import cn.hjljy.fastboot.common.enums.member.OrderTypeEnum;
import cn.hjljy.fastboot.common.enums.member.PayTypeEnum;
import cn.hjljy.fastboot.pojo.member.po.MemberOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 会员充值消费订单表 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-05-14
 */
public interface IMemberOrderInfoService extends IService<MemberOrderInfo> {

    /**
     * 创建会员订单  调用前需要判断会员是否存在
     * @param memberId 会员Id
     * @param orderSource 订单来源
     * @param payType 支付类型
     * @param money 金额
     * @param orderType 订单类型
     * @return 订单
     */
    MemberOrderInfo createOrder(Long memberId, OrderSourceEnum orderSource, PayTypeEnum payType, BigDecimal money, OrderTypeEnum orderType);
}
