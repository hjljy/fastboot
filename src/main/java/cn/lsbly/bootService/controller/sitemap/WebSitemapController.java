package cn.lsbly.bootService.controller.sitemap;


import cn.lsbly.bootService.common.result.ResultInfo;
import cn.lsbly.bootService.service.sitemap.IWebSitemapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.lsbly.cn）
 * @since 2020-09-19
 */
@RestController
@RequestMapping("/sitemap")
public class WebSitemapController {

    @Autowired
    IWebSitemapService sitemapService;

    @GetMapping("/{username}")
    public ResultInfo getSitemap(@PathVariable(value = "username",required=false) String username) {
        sitemapService.getSitemapByUsername(username);
        return ResultInfo.success();
    }
}

