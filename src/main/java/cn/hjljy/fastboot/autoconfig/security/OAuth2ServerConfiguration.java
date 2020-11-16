package cn.hjljy.fastboot.autoconfig.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author yichaofan
 * @apiNote OAuth2 认证服务器
 * @since 2020/11/16 18:33
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String CLIENT_ID = "client_3";  //客户端
    private static final String CLIENT_SECRET = "secret";   //secret客户端安全码
    private static final String GRANT_TYPE_PASSWORD = "password";   // 密码模式授权模式
    private static final String AUTHORIZATION_CODE = "authorization_code"; //授权码模式  授权码模式使用到了回调地址，是最为复杂的方式，通常网站中经常出现的微博，qq第三方登录，都会采用这个形式。
    private static final String REFRESH_TOKEN = "refresh_token";  //
    private static final String IMPLICIT = "implicit"; //简化授权模式
    private static final String GRANT_TYPE = "client_credentials";  //客户端模式
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String TRUST = "trust";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 24*60*60;          //
    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 48*60*60;        //
    private static final String RESOURCE_ID = "resource_id";    //指定哪些资源是需要授权验证的
    /** 密码加密编码器 */
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    /** 用户信息处理类 */
    @Autowired
    UserDetailsServiceImpl userDetailService;

    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String secret = new BCryptPasswordEncoder().encode(CLIENT_SECRET);  // 用 BCrypt 对密码编码
        //配置3个个客户端,一个用于password认证、一个用于client认证、一个用于authorization_code认证
        clients.inMemory()  // 使用in-memory存储
                .withClient(CLIENT_ID)    //client_id用来标识客户的Id
                .resourceIds(RESOURCE_ID)
                .authorizedGrantTypes(AUTHORIZATION_CODE,GRANT_TYPE, REFRESH_TOKEN,GRANT_TYPE_PASSWORD,IMPLICIT)  //允许授权类型
                .scopes(SCOPE_READ,SCOPE_WRITE,TRUST)  //允许授权范围
                .authorities("ROLE_CLIENT")  //客户端可以使用的权限
                .secret(secret)  //secret客户端安全码
                .autoApprove(true) // 为true 则不会被重定向到授权的页面，也不需要手动给请求授权,直接自动授权成功返回code
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)   //token 时间秒
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);//刷新token 时间 秒
    }
}
