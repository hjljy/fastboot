package cn.hjljy.fastboot.controller;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import cn.hjljy.fastboot.autoconfig.security.UserInfo;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.pojo.sys.dto.SysUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.security.Security;
import java.util.Map;

/**
 * @author yichaofan
 * @since 2020/10/16 18:06
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {
    @Autowired
    TokenEndpoint tokenEndpoint;

    @PostMapping(value = "/token")
    public ResultInfo token(Principal principal, @RequestParam Map<String, String> parameters) throws Exception {
        ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
        OAuth2AccessToken token = accessToken.getBody();
        Map<String, Object> additionalInformation = token.getAdditionalInformation();
        Long userId= (Long)additionalInformation.get("userId");
        String email= (String)additionalInformation.get("email");
        String username= (String)additionalInformation.get("username");
        String scope= (String)additionalInformation.get("scope");
        String nickName= (String)additionalInformation.get("nickName");
        SysUserDto userDto =new SysUserDto();
        userDto.setEmali(email);
        userDto.setNickName(nickName);
        userDto.setId(userId);
        return ResultInfo.success(userDto);
    }
}
