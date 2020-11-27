package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.pojo.member.dto.MemberBaseInfoDto;
import cn.hjljy.fastboot.pojo.member.po.MemberBaseInfo;
import cn.hjljy.fastboot.mapper.member.MemberBaseInfoMapper;
import cn.hjljy.fastboot.service.member.IMemberBaseInfoService;
import cn.hjljy.fastboot.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-26
 */
@Service
public class MemberBaseInfoServiceImpl extends BaseService<MemberBaseInfoMapper, MemberBaseInfo> implements IMemberBaseInfoService {

    @Override
    public List<MemberBaseInfoDto> getMemberBaseInfoList(String orgId, String keywords, Long levelId, Integer pageNo, Integer pageNum) {

        return new ArrayList<>();
    }
}
