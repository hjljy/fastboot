package cn.lsbly.bootService.service.sitemap;


import cn.lsbly.bootService.pojo.sitemap.dto.WebSitemapDto;
import cn.lsbly.bootService.pojo.sitemap.po.WebSitemapPo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 海加尔金鹰（www.lsbly.cn）
 * @since 2020-09-19
 */
public interface IWebSitemapService extends IService<WebSitemapPo> {

    /**
     * @author yichaofan
     * @apiNote  根据用户名获取网站信息 如果username为null，获取hjljy的网站
     * @since  23:50 2020/9/19
     * @param username
     *
     * @return java.util.List<cn.lsbly.bootService.pojo.sitemap.dto.WebSitemapDto>
     **/
    List<WebSitemapDto> getSitemapByUsername(String username);

    void addSitemap(WebSitemapDto sitemapDto);
}
