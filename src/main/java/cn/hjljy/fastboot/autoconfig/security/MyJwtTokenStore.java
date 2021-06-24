package cn.hjljy.fastboot.autoconfig.security;


import cn.hjljy.fastboot.common.constant.Oauth2Constant;
import cn.hjljy.fastboot.common.constant.RedisPrefixConstant;
import cn.hjljy.fastboot.autoconfig.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

/**
 * @author hjljy
 */
@Component
public class MyJwtTokenStore extends JwtTokenStore {

    @Autowired
    RedissonClient redissonClient;

    public MyJwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        super(jwtAccessTokenConverter);
    }

    /**
     * 设置token存储方式，将token存储到redis
     */
    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        //TODO 生成的token存入redis
        String scope = token.getScope().stream().findFirst().orElse(Oauth2Constant.SCOPE_BOOT);
        long userId = Long.parseLong(token.getAdditionalInformation().get("userId").toString());
        RMap<String, String> map = redissonClient.getMap(RedisPrefixConstant.LOGIN_USER_TOKEN + userId);
        map.put(scope, token.getValue());
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken token = super.readAccessToken(tokenValue);
        //TODO 校验token是否存在于redis里面 不存在表示过期
        String scope = token.getScope().stream().findFirst().orElse(Oauth2Constant.SCOPE_BOOT);
        long userId = Long.parseLong(token.getAdditionalInformation().get("userId").toString());
        RMap<String, String> map = redissonClient.getMap(RedisPrefixConstant.LOGIN_USER_TOKEN + userId);
        if (!map.containsKey(scope)) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }
        return token;
    }

    /**
     * 设置token存储方式，将token存储到redis
     */
    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        String scope = token.getScope().stream().findFirst().orElse(Oauth2Constant.SCOPE_BOOT);
        long userId = Long.parseLong(token.getAdditionalInformation().get("userId").toString());
        RMap<String, String> map = redissonClient.getMap(RedisPrefixConstant.LOGIN_USER_TOKEN + userId);
        map.remove(scope);
    }
}

