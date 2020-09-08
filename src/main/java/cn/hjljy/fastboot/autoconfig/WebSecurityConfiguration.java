package cn.hjljy.fastboot.autoconfig;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;
import java.util.Map;

/**
 * @author 海加尔金鹰 www.hjljy.cn
 * @apiNote websecurtiy权限校验处理
 * @since 2020/9/9 0:20
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    FastBootConfig config;

    /**
     * 描述:
     * http方式走 Spring Security 过滤器链，在过滤器链中，给请求放行，而web方式是不走 Spring Security 过滤器链。
     * 通常http方式用于请求的放行和限制，web方式用于放行静态资源
     *
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //读取配置文件当中的数据 不在这里写死
        Map<String, List<String>> request = config.getRequest();
        List<String> allow = request.get("allow");
        String[] array =new String[allow.size()];
        http
                .authorizeRequests()
                //用于配置直接放行的请求
                .antMatchers(allow.toArray(array)).permitAll()
                .anyRequest().authenticated()
                // 授权码模式需要
                .and().httpBasic()
                //禁用跨站伪造
                .and().csrf().disable();
    }
    /**
     * 描述: 静态资源放行，这里的放行，是不走 Spring Security 过滤器链
     **/
    @Override
    public void configure(WebSecurity web) {
        // 可以直接访问的数据
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/404.html")
                .antMatchers("/index.html")
                .antMatchers("/500.html")
                .antMatchers("/html/**")
                .antMatchers("/js/**");
    }
}

