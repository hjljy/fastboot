package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.pojo.member.po.MemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
public interface IMemberLevelService extends IService<MemberLevel> {
    /**
     * 获取机构默认的会员等级ID
     * @param orgId 机构ID
     * @return 会员等级信息
     */
    MemberLevel selectOrgDefaultLevelId(Long orgId);
}
