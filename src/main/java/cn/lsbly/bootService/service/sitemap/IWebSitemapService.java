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

    List<WebSitemapDto> getSitemapByUsername(String username);
}
