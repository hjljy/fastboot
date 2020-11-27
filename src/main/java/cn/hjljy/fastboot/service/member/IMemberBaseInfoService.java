package cn.hjljy.fastboot.service.member;

import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.dto.MemberListDto;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
public interface IMemberBaseInfoService extends IService<MemberBaseInfo> {

    /**
     * 分页查询数据
     * @param orgId  机构ID
     * @param keywords  关键字
     * @param levelId  等级ID
     * @param pageNo 页码
     * @param pageNum 页数
     * @return 会员列表信息
     */
    IPage<MemberBaseInfoDto> getMemberBaseInfoPageList(String orgId, String keywords, Long levelId, Integer pageNo, Integer pageNum);
}
