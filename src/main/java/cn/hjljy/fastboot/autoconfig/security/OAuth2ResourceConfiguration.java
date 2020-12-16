package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.List;

/**
 * @author yichaofan
 * @apiNote OAuth2 资源服务器
 * @since 2020/11/17 17:53
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    FastBootConfig fastBootConfig;
    @Autowired
    TokenStore tokenStore;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().csrf().disable();

        // 配置不登录可以访问 - 放行路径配置
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        List<String> requestAllow = fastBootConfig.getRequestAllow();
        String[] allow=new String[requestAllow.size()];
        String[] array = requestAllow.toArray(allow);
        registry.antMatchers(array).permitAll();
        registry.anyRequest().authenticated();
    }

}
