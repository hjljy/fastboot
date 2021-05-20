package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import cn.hjljy.fastboot.pojo.member.po.MemberMoneyRecord;
import cn.hjljy.fastboot.mapper.member.MemberMoneyRecordMapper;
import cn.hjljy.fastboot.service.member.IMemberMoneyRecordService;
import cn.hjljy.fastboot.service.BaseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 会员消费充值记录 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-05-14
 */
@Service
public class MemberMoneyRecordServiceImpl extends BaseService<MemberMoneyRecordMapper, MemberMoneyRecord> implements IMemberMoneyRecordService {

    @Override
    public void saveMoneyRecord(Long memberId, Long orderNum, BigDecimal money, BigDecimal giftMoney, ConsumeTypeEnum consumeType, BigDecimal balance, BigDecimal genBalance) {
        MemberMoneyRecord moneyRecord = new MemberMoneyRecord();
        moneyRecord.setMemberId(memberId);
        moneyRecord.setOrderNum(orderNum);
        moneyRecord.setMoney(money);
        moneyRecord.setGiveMoney(giftMoney);
        moneyRecord.setType(consumeType);
        moneyRecord.setBalance(balance);
        moneyRecord.setGenBalance(genBalance);
        this.save(moneyRecord);
    }
}
