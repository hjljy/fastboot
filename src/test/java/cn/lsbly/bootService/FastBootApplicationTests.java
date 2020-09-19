package cn.lsbly.bootService;

import cn.lsbly.bootService.autoconfig.config.FastBootConfig;
import cn.lsbly.bootService.pojo.sitemap.dto.WebSitemapDto;
import cn.lsbly.bootService.service.sitemap.IWebSitemapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class FastBootApplicationTests {

    @Autowired
    FastBootConfig config;

    @Autowired
    IWebSitemapService webSitemapService;

    @Test
    public void getAllow() {
        Map<String, List<String>> request = config.getRequest();
        List<String> allow = request.get("allow");
        System.out.println(allow.toString());
        List<WebSitemapDto> sitemapByUsername = webSitemapService.getSitemapByUsername("");
        System.out.println(sitemapByUsername.size());
    }
}
