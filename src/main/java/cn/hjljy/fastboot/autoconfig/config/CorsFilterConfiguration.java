package cn.hjljy.fastboot.autoconfig.config;

import cn.hjljy.fastboot.autoconfig.annotation.EnableCors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * @author hjljy
 * @since  2020/6/4 11:12
 * @apiNote 支持跨域CORS请求  如果需要开启需要在启动类上添加注解 {@link cn.hjljy.fastboot.autoconfig.annotation.EnableCors}
 * @see EnableCors
 */
@Configuration
@ConditionalOnBean(annotation = EnableCors.class)
public class CorsFilterConfiguration {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        // TODO 可以设置部分地址可以进行访问
        List<String> origins = Arrays.asList("http://www.hjljy.cn", "http://api.hjljy.cn");
        config.setAllowedOrigins(origins);
        // 设置所有地址的请求都可以
        config.addAllowedOrigin("*");

        // 设置为允许所有请求头信息
        config.addAllowedHeader("*");

        // 设置为支持所有请求方式
        config.addAllowedMethod("*");

        // 设置所有的请求路径都可以访问
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        //设置优先级为最高
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
