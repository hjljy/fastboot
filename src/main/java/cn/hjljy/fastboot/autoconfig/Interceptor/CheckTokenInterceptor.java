package cn.hjljy.fastboot.autoconfig.Interceptor;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 全局check_token
 */
@Component
@Slf4j
public class CheckTokenInterceptor  implements HandlerInterceptor {

    @Autowired
    FastBootConfig fastBootConfig;

    @Autowired
    RestTemplate restTemplate;

    @Value("${fastboot.oauth2.checkTokenUrl}")
    String checkTokenUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String attribute = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = attribute.replace(OAuth2AccessToken.BEARER_TYPE, "").trim();
        ResultInfo entity = restTemplate.getForObject(checkTokenUrl+token, ResultInfo.class);
        if(ResultCode.SUCCESS.getCode()==entity.getCode()){
           return true;
        }
        String result = JacksonUtil.obj2String(entity);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
        writer.close();
        return false;
    }
}
