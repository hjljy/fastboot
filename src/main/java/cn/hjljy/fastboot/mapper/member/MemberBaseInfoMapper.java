package cn.hjljy.fastboot.mapper.member;

import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
public interface MemberBaseInfoMapper extends BaseMapper<MemberBaseInfo> {
    /**
     * 分页查询数据
     * @param orgId  机构ID
     * @param keywords  关键字
     * @param levelId  等级ID
     * @return 会员列表信息
     */
    IPage<MemberBaseInfoDto> getMemberBaseInfoPageList(IPage<MemberBaseInfoDto> page, @Param("orgId") String orgId,@Param("keywords") String keywords,@Param("levelId") Long levelId);
}
