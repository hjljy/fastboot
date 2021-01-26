package cn.hjljy.fastboot.controller;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.common.constant.OAuth2Constant;
import cn.hjljy.fastboot.common.constant.RedisPrefixConstant;
import cn.hjljy.fastboot.common.enums.StatusEnum;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

/**
 * @author yichaofan
 * @since 2020/10/16 18:06
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {
    @Autowired
    TokenEndpoint tokenEndpoint;

    @Autowired
    CheckTokenEndpoint checkTokenEndpoint;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    ISysUserService userService;

    @Autowired
    RedissonClient redissonClient;

    /**
     * 登录接口
     *
     * @param principal  用户信息
     * @param parameters 登录参数
     * @return token
     * @throws Exception 登录异常
     */
    @PostMapping(value = "/token")
    public ResultInfo token(Principal principal, @RequestParam Map<String, String> parameters) throws Exception {
        String scope = parameters.get("scope");
        if (null == parameters.get("scope")) {
            parameters.put("scope", OAuth2Constant.SCOPE_BOOT);
        }
        ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
        OAuth2AccessToken token = accessToken.getBody();
        if (null == token){
            return ResultInfo.error(ResultCode.TOKEN_NOT_CREATE);
        }
        Long userId = (Long)(token.getAdditionalInformation().get("userId"));
        RMap<Object, Object> map = redissonClient.getMap(RedisPrefixConstant.LOGIN_USER_TOKEN+userId);
        map.put(scope, token.getValue());
        return ResultInfo.success(token);
    }

    /**
     * 登录操作处理
     *
     * @param token token
     * @return 操作结果
     */
    @RequestMapping("/logout")
    public ResultInfo logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        ResultInfo success = ResultInfo.success();
        if (StringUtils.isEmpty(token)) {
            return success;
        }
        String tokenValue = token.replace(OAuth2AccessToken.BEARER_TYPE, "").trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StringUtils.isEmpty(accessToken.getValue())) {
            return success;
        }
        // 删除现有用户的token 如果使用JWTTokenStore 这里是不生效的，没有任何意义的，需要重写这个方法
        tokenStore.removeAccessToken(accessToken);
        // 直接删除redis里面的token 这样在进行check_token的时候就会判断token是否过期
        RMap<Object, Object> map = redissonClient.getMap(RedisPrefixConstant.LOGIN_USER_TOKEN+ SecurityUtils.getUserId());
        Set<String> scopes = accessToken.getScope();
        map.fastRemove(scopes.toArray());
        return success;
    }

    @RequestMapping("/check_token")
    public ResultInfo customCheckToken(@RequestParam("token") String value) {
        ResultInfo success = ResultInfo.success();
        try {
            // 校验信息
            checkTokenEndpoint.checkToken(value);
            // 自定义校验TOKEN
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(value);
            Long userId = (Long) accessToken.getAdditionalInformation().get("userId");
            SysUser user = userService.getById(userId);
            if (null == user) {
                success = ResultInfo.error(ResultCode.USER_NOT_FOUND);
            } else if (StatusEnum.DISABLE.getCode().equals(user.getEnable())) {
                success = ResultInfo.error(ResultCode.USER_DISABLE);
            }
            //TODO 由于JWT生成的token是无法主动过期的，需要自己设置token过期策略（例如生成的token存进redis ,判断token是否一致）
            RMap<Object, Object> map = redissonClient.getMap(RedisPrefixConstant.LOGIN_USER_TOKEN+ SecurityUtils.getUserId());
            if (!map.containsValue(value)) {
                success = ResultInfo.error(ResultCode.TOKEN_EXPIRED);
            }
        } catch (InvalidTokenException e) {
            success = ResultInfo.error(ResultCode.TOKEN_EXPIRED);
        }
        return success;
    }


}
