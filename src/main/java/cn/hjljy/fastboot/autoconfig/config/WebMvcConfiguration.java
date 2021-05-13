package cn.hjljy.fastboot.autoconfig.config;

import cn.hjljy.fastboot.autoconfig.filter.RequestBodyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yichaofan
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    FastBootConfig config;

    /**
     * 处理RequestBody 流数据无法多次读取的问题
     */
    @Bean
    public FilterRegistrationBean<RequestBodyFilter> setLogServiceFilter() {
        FilterRegistrationBean<RequestBodyFilter> registrationBean = new FilterRegistrationBean<>();
        RequestBodyFilter requestBodyFilter = new RequestBodyFilter();
        registrationBean.setFilter(requestBodyFilter);
        registrationBean.setName("过滤器处理请求body参数");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
