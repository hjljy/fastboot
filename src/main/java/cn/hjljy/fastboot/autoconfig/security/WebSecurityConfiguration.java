package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author hjljy
 * @apiNote webSecurity权限校验处理
 * @since 2020/9/9 0:20
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    FastBootConfig config;

    /**
     * 描述:
     * http方式走 Spring Security 过滤器链，在过滤器链中，给请求放行，而web方式是不走 Spring Security 过滤器链。
     * 通常http方式用于请求的放行和限制，web方式用于放行静态资源
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //读取配置文件当中的数据 不在这里写死 方便后续如果有新的需要放行的请求，不用修改代码重启项目
        List<String> allow = config.getRequestAllow();
        String[] array = new String[allow.size()];
        http
                .authorizeRequests()
                //用于配置直接放行的请求
                .antMatchers(allow.toArray(array)).permitAll()
                //其余请求都需要验证
                .anyRequest().authenticated()
                .and()
                //默认logout
                .logout()
                .logoutUrl("/oauth/logout")
                //清除会话
                .invalidateHttpSession(true)
                //清除授权
                .clearAuthentication(true)
                //授权码模式需要
                .and().httpBasic()
                //禁用跨站伪造
                .and().csrf().disable();
    }

    /**
     * 描述: 密码加密算法 BCrypt 推荐使用
     **/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 描述: 注入AuthenticationManager管理器
     **/
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

