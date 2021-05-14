package cn.hjljy.fastboot.controller;

import cn.hjljy.fastboot.common.constant.Oauth2Constant;
import cn.hjljy.fastboot.common.constant.RedisPrefixConstant;
import cn.hjljy.fastboot.common.enums.StatusEnum;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.common.utils.RequestUtil;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * @author yichaofan
 * @since 2020/10/16 18:06
 */
@RestController
@RequestMapping("/oauth")
@Api(value = "登录认证相关接口", tags = "登录认证相关接口")
@Slf4j
public class OauthController {
    @Autowired
    TokenEndpoint tokenEndpoint;

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
    @ApiOperation(value = "登录获取token")
    public OAuth2AccessToken token(Principal principal, @RequestParam Map<String, String> parameters) throws Exception {
        parameters.putIfAbsent("scope", Oauth2Constant.SCOPE_BOOT);
        ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
        return accessToken.getBody();
    }
    
    /**
     * 登录退出操作处理
     *
     * @param request 请求
     * @return 操作结果
     */
    @GetMapping("/logout")
    @ApiOperation(value = "登录退出接口")
    public ResultInfo<Object> logout(HttpServletRequest request) {
        ResultInfo<Object> success = new ResultInfo<>();
        String token = RequestUtil.getToken(request);
        // 自定义校验TOKEN
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        // 移除TOKEN
        tokenStore.removeAccessToken(accessToken);
        return success;
    }

    @GetMapping("/check_token")
    @ApiOperation(value = "token校验接口")
    @Deprecated
    public ResultInfo<Object> customCheckToken(@RequestParam("token") String token) {
        try {
            // 自定义校验TOKEN
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            long userId = Long.parseLong(accessToken.getAdditionalInformation().get("userId").toString());
            // 由于JWT生成的token是无法主动过期的，需要自己设置token过期策略（例如生成的token存进redis ,判断token是否一致）
            RMap<Object, Object> map = redissonClient.getMap(RedisPrefixConstant.LOGIN_USER_TOKEN + userId);
            if (!map.containsValue(token)) {
                return ResultInfo.error(ResultCode.TOKEN_EXPIRED);
            }
            // 判断当前用户是否被删除或者禁用
            SysUser user = userService.getById(userId);
            if (null == user) {
                return ResultInfo.error(ResultCode.USER_NOT_FOUND);
            } else if (StatusEnum.DISABLE.getCode().equals(user.getEnable())) {
                return ResultInfo.error(ResultCode.USER_DISABLE);
            }
            // TODO 判断用户所属机构是否被禁用
        } catch (InvalidTokenException e) {
            return ResultInfo.error(ResultCode.TOKEN_NOT_CREATE);
        }
        return ResultInfo.success();
    }


}
