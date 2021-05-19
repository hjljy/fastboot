package cn.hjljy.fastboot.service.member.impl;

import cn.hjljy.fastboot.pojo.member.dto.MemberUpvDto;
import cn.hjljy.fastboot.pojo.member.po.MemberUpv;
import cn.hjljy.fastboot.mapper.member.MemberUpvMapper;
import cn.hjljy.fastboot.service.member.IMemberUpvService;
import cn.hjljy.fastboot.service.BaseService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 会员权益成长值获取扣减计算规则表 服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-02-23
 */
@Service
public class MemberUpvServiceImpl extends BaseService<MemberUpvMapper, MemberUpv> implements IMemberUpvService {

    @Override
    public MemberUpvDto getByOrgId(Long orgId) {
        MemberUpvDto dto = null;
        MemberUpv upv = baseMapper.selectById(orgId);
        //如果存在就直接返回
        if (null != upv) {
            dto = new MemberUpvDto();
            BeanUtil.copyProperties(upv, dto);
        }
        //不存在就返回null
        return dto;
    }

    @Override
    public Boolean editUpv(MemberUpvDto dto) {
        MemberUpv memberUpv = baseMapper.selectById(dto.getOrgId());
        //如果存在规则就更新
        if (null != memberUpv) {
            BeanUtil.copyProperties(dto, memberUpv);
            return this.updateById(memberUpv);
        }
        //如果不存在就新增
        memberUpv = new MemberUpv();
        BeanUtil.copyProperties(dto, memberUpv);
        return this.save(memberUpv);
    }
}
