package cn.hjljy.fastboot.autoconfig.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author 海加尔金鹰
 * @apiNote TODO
 * @since 2020/9/20 22:32
 **/
@Configuration
@EnableAuthorizationServer
public class Oauth2Configuration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 配置token获取和验证时的策略
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()
                .withClient("client")
                // secret密码配置从 Spring Security 5.0开始必须以 {加密方式}+加密后的密码 这种格式填写
                *//*
         *   当前版本5新增支持加密方式：
         *   bcrypt - BCryptPasswordEncoder (Also used for encoding)
         *   ldap - LdapShaPasswordEncoder
         *   MD4 - Md4PasswordEncoder
         *   MD5 - new MessageDigestPasswordEncoder("MD5")
         *   noop - NoOpPasswordEncoder
         *   pbkdf2 - Pbkdf2PasswordEncoder
         *   scrypt - SCryptPasswordEncoder
         *   SHA-1 - new MessageDigestPasswordEncoder("SHA-1")
         *   SHA-256 - new MessageDigestPasswordEncoder("SHA-256")
         *   sha256 - StandardPasswordEncoder
         *//*
                .secret("{noop}secret")
                .scopes("all")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .autoApprove(true);*/
        clients.inMemory()
                .withClient("client").secret("123456")
                .scopes("all")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置tokenStore，保存到redis缓存中
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(memoryTokenStore())
                // 不添加userDetailsService，刷新access_token时会报错
                .userDetailsService(userDetailsService);

        // 使用最基本的InMemoryTokenStore生成token
        //endpoints.authenticationManager(authenticationManager).tokenStore(memoryTokenStore());


    }

    // 使用最基本的InMemoryTokenStore生成token
    @Bean
    public TokenStore memoryTokenStore() {
        return new InMemoryTokenStore();
    }
}
