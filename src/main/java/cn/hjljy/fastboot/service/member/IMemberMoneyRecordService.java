package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import cn.hjljy.fastboot.pojo.member.po.MemberMoneyRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 会员消费充值记录 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-05-14
 */
public interface IMemberMoneyRecordService extends IService<MemberMoneyRecord> {

    /**
     * 保存金额记录
     *
     * @param memberId    会员id
     * @param orderNum    订单num
     * @param money       金额
     * @param giftMoney   赠送金额
     * @param consumeType 消费类型
     * @param balance     余额
     * @param genBalance  赠送余额
     */
    void saveMoneyRecord(Long memberId, Long orderNum, BigDecimal money, BigDecimal giftMoney, ConsumeTypeEnum consumeType, BigDecimal balance, BigDecimal genBalance);
}
