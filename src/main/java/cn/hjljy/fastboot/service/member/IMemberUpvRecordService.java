package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.common.enums.member.ConsumeTypeEnum;
import cn.hjljy.fastboot.pojo.member.po.MemberUpvRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-02-23
 */
public interface IMemberUpvRecordService extends IService<MemberUpvRecord> {


    /**
     * 保存成长价值记录
     *
     * @param memberId    会员id
     * @param growthValue 成长价值
     * @param consumeType 消费类型
     * @param type        类型
     * @param remark      备注
     */
    void saveGrowthValueRecord(Long memberId, Integer growthValue, ConsumeTypeEnum consumeType, int type,String remark);
}
