package cn.hjljy.fastboot.autoconfig.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     * JWT 令牌转换器
     * @return 返回jwt处理器
     */
    @Bean("jwtAccessTokenConverter")
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwt = new JwtAccessTokenConverter(){
            /**
             * 用户信息JWT加密
             */
            @Override
            protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
                UserInfo user = (UserInfo) authentication.getUserAuthentication().getPrincipal();
                Set<String> tokenScope = token.getScope();
                String scopeTemp = " ";
                if(tokenScope!=null&&tokenScope.size()>0){
                    scopeTemp=tokenScope.iterator().next();
                }
                String scope =scopeTemp;
                //将额外的参数信息存入，用于生成token
                Map<String, Object> data = new HashMap<String, Object>(4){{
                    put("userId", user.getUserId());
                    put("username", user.getUsername());
                    put("email", user.getEmail());
                    put("nickName", user.getNickName());
                    put("authorities", user.getAuthorities());
                    put("scope",scope);
                }};
                //自定义TOKEN包含的信息
                token.setAdditionalInformation(data);
                return super.encode(accessToken, authentication);
            }

            /**
             * 用户信息JWT
             */
            @Override
            protected Map<String, Object> decode(String token) {
                //解析请求当中的token  可以在解析后的map当中获取到上面加密的数据信息
                Map<String, Object> decode = super.decode(token);
                Long userId = (Long)decode.get("userId");
                String username = (String)decode.get("username");
                String email = (String)decode.get("email");
                String nickName = (String)decode.get("nickName");
                String scope = (String)decode.get("scope");
                List<GrantedAuthority> grantedAuthorityList=new ArrayList<>();
                //注意这里获取到的权限 虽然数据库存的权限是 "sys:menu:add"  但是这里就变成了"{authority=sys:menu:add}" 所以使用@PreAuthorize("hasAuthority('{authority=sys:menu:add}')")
                Object authorities = decode.get("authorities");
                if (authorities instanceof List<?>) {
                    List<?> list = (List<?>) authorities;
                    for (Object o : list) {
                        if (o instanceof LinkedHashMap<?, ?>) {
                            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) o;
                            Class<?> keyClass = map.entrySet().stream().findFirst().map(entry -> entry.getKey().getClass()).orElse(null);
                            if (String.class.equals(keyClass)) {
                                Object authority = map.get("authority");
                                if (authority instanceof String) {
                                    String auth = (String) authority;
                                    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(auth);
                                    grantedAuthorityList.add(grantedAuthority);
                                }
                            }
                        }
                    }
                }
                UserInfo userInfo =new UserInfo(username,"N/A",userId, grantedAuthorityList);
                userInfo.setNickName(nickName);
                userInfo.setEmail(email);
                userInfo.setScope(scope);
                //需要将解析出来的用户存入全局当中，不然无法转换成自定义的user类
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfo,null, grantedAuthorityList);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                decode.put("user_name",userInfo);
                return decode;
            }
        };

        /** JWT密钥 */
        String signingKey = "fastboot";
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
        return new MyJwtTokenStore(jwtAccessTokenConverter());
    }
}
