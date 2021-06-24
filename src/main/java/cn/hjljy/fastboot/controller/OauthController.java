package cn.hjljy.fastboot.controller;

import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * @author 海加尔金鹰
 * @since 2020/10/16 18:06
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {
    @Autowired
    TokenEndpoint tokenEndpoint;

    @Autowired
    TokenStore tokenStore;

    /**
     * 登录接口
     * @param principal 用户信息
     * @param parameters 登录参数
     * @return token
     * @throws Exception 登录异常
     */
    @PostMapping(value = "/token")
    public ResultInfo<String> token(Principal principal, @RequestParam Map<String, String> parameters) throws Exception {
        ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
        OAuth2AccessToken token = accessToken.getBody();
        assert token != null;
        return ResultInfo.success(token.getValue());
    }

    /**
     * 登录操作处理
     * @param token token
     * @return 操作结果
     */
    @RequestMapping("/logout")
    public ResultInfo<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token)  {
        ResultInfo<Boolean> success = ResultInfo.success();
        if (StringUtils.isEmpty(token)) {
            return success;
        }
        String tokenValue = token.replace(OAuth2AccessToken.BEARER_TYPE, "").trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StringUtils.isEmpty(accessToken.getValue())) {
            return success;
        }
        // 删除现有用户的token 使用JWTTokenStore 这里是不生效的，需要重写这个方法
        tokenStore.removeAccessToken(accessToken);
        return success;
    }

    @RequestMapping("/check_token")
    public ResultInfo<Boolean> customCheckToken(@RequestParam("token") String value){
        ResultInfo<Boolean> success = ResultInfo.success();
        try {
            // 校验TOKEN
             tokenStore.readAccessToken(value);
        } catch (InvalidTokenException e) {
            success=ResultInfo.error(ResultCode.TOKEN_EXPIRED);
        }
        return success;
    }


}
