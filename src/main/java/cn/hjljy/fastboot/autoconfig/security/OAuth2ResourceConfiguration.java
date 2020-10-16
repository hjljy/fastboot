package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.List;

/**
 * @author 海加尔金鹰
 * @apiNote oauth2 资源服务 这个表示是资源服务器
 * @since 2020/9/22 22:20
 **/
@Configuration
@EnableResourceServer
public class OAuth2ResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    FastBootConfig config;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        List<String> allow = config.getRequestAllow();
        String[] array = new String[allow.size()];
        http.exceptionHandling().and().authorizeRequests().antMatchers(allow.toArray(array)).permitAll()
                .and().authorizeRequests().antMatchers("/**").authenticated();
    }
}
