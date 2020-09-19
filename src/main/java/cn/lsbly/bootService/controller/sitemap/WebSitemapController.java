package cn.lsbly.bootService.controller.sitemap;


import cn.lsbly.bootService.common.result.ResultInfo;
import cn.lsbly.bootService.pojo.sitemap.dto.WebSitemapDto;
import cn.lsbly.bootService.service.sitemap.IWebSitemapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Api(value = "网址导航",tags = "网址导航")
public class WebSitemapController {

    @Autowired
    IWebSitemapService sitemapService;

    @GetMapping("/{username}")
    @ApiOperation(value = "根据用户名获取网址导航")
    public ResultInfo getSitemap(@PathVariable(value = "username",required=false) String username) {
        List<WebSitemapDto> sitemap = sitemapService.getSitemapByUsername(username);
        return ResultInfo.success(sitemap);
    }

    @PostMapping("/")
    @ApiOperation(value = "新增网址导航")
    public ResultInfo addSitemap(@RequestBody WebSitemapDto sitemapDto) {
        sitemapService.addSitemap(sitemapDto);
        return ResultInfo.success();
    }
}

