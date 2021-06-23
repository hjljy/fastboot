package cn.hjljy.fastboot.autoconfig.security;

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

    private static final String CLIENT_ID = "client";  //客户端
    private static final String CLIENT_SECRET = "123456";   //secret客户端安全码
    private static final String GRANT_TYPE_PASSWORD = "password";   // 密码模式授权模式
    private static final String AUTHORIZATION_CODE = "authorization_code"; //授权码模式  授权码模式使用到了回调地址，是最为复杂的方式，通常网站中经常出现的微博，qq第三方登录，都会采用这个形式。
    private static final String REFRESH_TOKEN = "refresh_token";  //
    private static final String IMPLICIT = "implicit"; //简化授权模式
    private static final String GRANT_TYPE = "client_credentials";  //客户端模式
    private static final String SCOPE_WEB = "web";   //授权范围  web端
    private static final String SCOPE_IOS = "ios";   //授权范围  ios端
    private static final String SCOPE_ANDROID = "android";
    private static final String SCOPE_BOOT = "boot"; //授权范围  项目名称
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 30*24*60*60;       //token 有效时间 一个月
    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 30*24*60*60;      //刷新token有效时间 一个月
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
        String secret = new BCryptPasswordEncoder().encode(CLIENT_SECRET);  // 用 BCrypt 对密码编码
        //配置客户端信息
        clients.inMemory()  // 使用in-memory存储
                .withClient(CLIENT_ID)    //client_id用来标识客户的Id
                .authorizedGrantTypes(AUTHORIZATION_CODE,GRANT_TYPE, REFRESH_TOKEN,GRANT_TYPE_PASSWORD,IMPLICIT)  //允许授权类型
                .scopes(SCOPE_WEB,SCOPE_IOS,SCOPE_ANDROID,SCOPE_BOOT)  //允许授权范围
                .authorities("ROLE_CLIENT")  //客户端可以使用的权限
                .secret(secret)  //secret客户端安全码
                .autoApprove(true) // 为true 则不会被重定向到授权的页面，也不需要手动给请求授权,直接自动授权成功返回code
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)   //token 时间秒
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);//刷新token 时间 秒
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
     *
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
