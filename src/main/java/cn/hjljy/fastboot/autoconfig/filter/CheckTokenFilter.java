package cn.hjljy.fastboot.autoconfig.filter;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 全局check_token
 */
@Component
@Slf4j
public class CheckTokenFilter implements Filter, Ordered {

    @Autowired
    FastBootConfig fastBootConfig;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       if( servletRequest instanceof HttpServletRequest){
           HttpServletRequest request = (HttpServletRequest) servletRequest;
           String path = request.getServletPath();
           List<String> requestAllow = fastBootConfig.getRequestAllow();
           AntPathMatcher matcher = new AntPathMatcher();
           for (String s : requestAllow) {
               if (matcher.match(s, path)) {
                   filterChain.doFilter(servletRequest, servletResponse);
                   return;
               }
           }
           String attribute = request.getHeader(HttpHeaders.AUTHORIZATION);
           String token = attribute.replace(OAuth2AccessToken.BEARER_TYPE, "").trim();
           ResultInfo entity = restTemplate.getForObject("http://127.0.0.1:8090/oauth/check_token?token="+token, ResultInfo.class);
           HttpServletResponse response = (HttpServletResponse)servletResponse;
           if(ResultCode.ERROR.getCode()==entity.getCode()||ResultCode.TOKEN_EXPIRED.getCode()==entity.getCode()){
               response.setStatus(HttpStatus.UNAUTHORIZED.value());
               log.info("请求：{} 携带的token:{} 过期了",path,token);
           }
       }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
