package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.common.constant.Oauth2Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Collections;


/**
 * @author yichaofan
 * @apiNote OAuth2 认证服务器
 * @since 2020/11/16 18:33
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {
    /**
     * 描述：注入密码加密编码器 进行密码加密
     */
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    /**
     * 描述：注入用户信息处理类 处理用户账号信息
     */
    @Autowired
    UserDetailsServiceImpl userDetailService;
    /**
     * 描述：注入token生成器  处理token的生成方式
     */
    @Autowired
    TokenStore tokenStore;
    /**
     * 描述: 注入AuthenticationManager管理器
     */
    @Autowired
    AuthenticationManager authenticationManager;
    /**
     * 描述: 注入jwtAccessTokenConverter 增强token
     */
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 用 BCrypt 对密码编码
        String secret = new BCryptPasswordEncoder().encode(Oauth2Constant.CLIENT_SECRET);
        //配置客户端信息 使用in-memory存储
        clients.inMemory()
                //client_id用来标识客户的Id
                .withClient(Oauth2Constant.CLIENT_ID)
                //允许授权类型
                .authorizedGrantTypes(Oauth2Constant.AUTHORIZATION_CODE, Oauth2Constant.GRANT_TYPE, Oauth2Constant.REFRESH_TOKEN, Oauth2Constant.GRANT_TYPE_PASSWORD, Oauth2Constant.IMPLICIT)
                //允许授权范围
                .scopes(Oauth2Constant.SCOPE_WEB,Oauth2Constant.SCOPE_IOS, Oauth2Constant.SCOPE_ANDROID, Oauth2Constant.SCOPE_BOOT)
                //客户端可以使用的权限
                .authorities("ROLE_CLIENT")
                //secret客户端安全码
                .secret(secret)
                // 为true 则不会被重定向到授权的页面，也不需要手动给请求授权,直接自动授权成功返回code
                .autoApprove(true)
                //token 时间秒
                .accessTokenValiditySeconds(Oauth2Constant.ACCESS_TOKEN_VALIDITY_SECONDS)
                //刷新token 时间 秒
                .refreshTokenValiditySeconds(Oauth2Constant.REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                // 允许表单登录
                .allowFormAuthenticationForClients()
                // 密码加密编码器
                .passwordEncoder(passwordEncoder)
                // 允许所有的checkToken请求
                .checkTokenAccess("permitAll()");
    }

    /**
     * 配置令牌
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore)
                // 认证管理器 - 在密码模式必须配置
                .authenticationManager(authenticationManager)
                // 自定义校验用户service
                .userDetailsService(userDetailService)
                // 是否能重复使用 refresh_token
                .reuseRefreshTokens(false);
        // 设置令牌增强 JWT 转换
        TokenEnhancerChain enhancer = new TokenEnhancerChain();
        enhancer.setTokenEnhancers(Collections.singletonList(jwtAccessTokenConverter));
        endpoints.tokenEnhancer(enhancer);
    }
}
