package cn.lsbly.bootService.service.sitemap.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.lsbly.bootService.common.constants.CommonConst;
import cn.lsbly.bootService.pojo.sitemap.dto.WebSitemapDto;
import cn.lsbly.bootService.pojo.sitemap.po.WebSitemapPo;
import cn.lsbly.bootService.mapper.sitemap.WebSitemapMapper;
import cn.lsbly.bootService.service.sitemap.IWebSitemapService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 海加尔金鹰（www.lsbly.cn）
 * @since 2020-09-19
 */
@Service
public class WebSitemapServiceImpl extends ServiceImpl<WebSitemapMapper, WebSitemapPo> implements IWebSitemapService {

    @Override
    public List<WebSitemapDto> getSitemapByUsername(String username) {
        List<WebSitemapDto> result =new ArrayList<>();
        if(StringUtils.isBlank(username)){
            username= CommonConst.USERNAME;
        }
        QueryWrapper<WebSitemapPo>  queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda().eq(WebSitemapPo::getUsername,username);
        List<WebSitemapPo> list = this.list(queryWrapper);
        for (WebSitemapPo webSitemapPo : list) {
            WebSitemapDto dto=new WebSitemapDto();
            BeanUtil.copyProperties(webSitemapPo,dto,"id");
            result.add(dto);
        }
        return result;
    }

    @Override
    public void addSitemap(WebSitemapDto dto) {
        WebSitemapPo po=new WebSitemapPo();
        if(StringUtils.isBlank(dto.getIcon())){
            dto.setIcon(CommonConst.ICON);
        }
        if (StringUtils.isBlank(dto.getUsername())){
            dto.setUsername(CommonConst.USERNAME);
        }
        BeanUtil.copyProperties(dto,po);
        this.save(po);
    }
}
