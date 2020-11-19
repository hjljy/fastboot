package cn.hjljy.fastboot.autoconfig.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.*;

/**
 * @author yichaofan
 * @apiNote token处理
 * @since 2020/11/17 17:04
 */
@Configuration
public class TokenConfig {
    /** JWT密钥 */
    private String signingKey = "fastboot";

    /**
     * JWT 令牌转换器
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwt = new JwtAccessTokenConverter(){
            @Override
            protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;

                //设置TOKEN过期时间为1个月
                token.setExpiration(new Date(System.currentTimeMillis() +60*60*24*30));
                UserInfo user = (UserInfo) authentication.getUserAuthentication().getPrincipal();
                Set<String> tokenScope = token.getScope();
                String scopeTemp = " ";
                if(tokenScope!=null&&tokenScope.size()>0){
                    scopeTemp=tokenScope.iterator().next();
                }
                String scope =scopeTemp;
                Map<String, Object> data = new HashMap<String, Object>(4){{
                    put("user_id", user.getUserId());
                    put("email", user.getEmail());
                    put("roleDtos",user.getRoleDtos());
                    put("nickName", user.getNickName());
                    put("scope",scope);
                }};
                //自定义TOKEN包含的信息
                token.setAdditionalInformation(data);
                return super.encode(accessToken, authentication);
            }
        };
        jwt.setSigningKey(signingKey);
        return jwt;
    }

    /**
     * 配置 token 如何生成
     * 1. InMemoryTokenStore 基于内存存储
     * 2. JdbcTokenStore 基于数据库存储
     * 3. JwtTokenStore 使用 JWT 存储 该方式可以让资源服务器自己校验令牌的有效性而不必远程连接认证服务器再进行认证
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }
}
