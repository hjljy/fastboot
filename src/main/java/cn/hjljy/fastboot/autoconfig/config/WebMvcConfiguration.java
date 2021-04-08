package cn.hjljy.fastboot.autoconfig.config;

import cn.hjljy.fastboot.autoconfig.Interceptor.CheckTokenInterceptor;
import cn.hjljy.fastboot.autoconfig.filter.RequestBodyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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

    @Bean
    public CheckTokenInterceptor getCheckTokenInterceptor() {
        return new CheckTokenInterceptor();
    }

    /**
     * 注入全局校验check_token拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> allow = config.getRequestAllow();
        String[] array = new String[allow.size()];
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(getCheckTokenInterceptor());
        //不拦截的路径
        registration.excludePathPatterns(allow.toArray(array));
        //所有路径都被拦截
        registration.addPathPatterns("/**");
    }
}
