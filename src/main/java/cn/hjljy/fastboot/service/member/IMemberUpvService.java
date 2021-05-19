package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.pojo.member.dto.MemberUpvDto;
import cn.hjljy.fastboot.pojo.member.po.MemberUpv;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员权益成长值获取扣减计算规则表 服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-02-23
 */
public interface IMemberUpvService extends IService<MemberUpv> {

    /**
     * 获取机构的会员成长值扣减计算规则
     *
     * @param orgId 机构id
     * @return 返回扣减计算规则
     */
    MemberUpvDto getByOrgId(Long orgId);

    /**
     * 编辑机构会员成长值扣减计算规则
     *
     * @param dto 更新信息
     * @return 返回更新结果
     */
    Boolean editUpv(MemberUpvDto dto);
}
