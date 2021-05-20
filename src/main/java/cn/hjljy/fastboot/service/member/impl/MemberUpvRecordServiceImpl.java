package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import cn.hjljy.fastboot.pojo.member.po.MemberUpvRecord;
import cn.hjljy.fastboot.mapper.member.MemberUpvRecordMapper;
import cn.hjljy.fastboot.service.member.IMemberUpvRecordService;
import cn.hjljy.fastboot.service.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-02-23
 */
@Service
public class MemberUpvRecordServiceImpl extends BaseService<MemberUpvRecordMapper, MemberUpvRecord> implements IMemberUpvRecordService {

    @Override
    public void saveGrowthValueRecord(Long memberId, Integer growthValue, ConsumeTypeEnum consumeType, int type, String remark) {
        MemberUpvRecord record = new MemberUpvRecord();
        record.setMemberId(memberId);
        record.setType(type);
        record.setGrowthValue(growthValue);
        record.setOperationType(consumeType);
        record.setRemark(remark);
        record.setCreateTime(LocalDateTime.now());
        this.save(record);
    }
}
