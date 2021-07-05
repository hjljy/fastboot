package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hjljy
 * Oauth2异常信息返回处理
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error(e.getMessage());
        if(e instanceof BadCredentialsException){
            //如果是client_id和client_secret相关异常 返回自定义的数据格式
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            ResultInfo<Boolean> result = ResultInfo.error(ResultCode.INVALID_CLIENT);
            result.setData(false);
            response.getWriter().write(JacksonUtil.obj2String(result));
        }else if(e instanceof InsufficientAuthenticationException){
            //如果是没有携带token
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            ResultInfo<Boolean> result = ResultInfo.error(ResultCode.TOKEN_NOT_FOUND);
            result.setData(false);
            response.getWriter().write(JacksonUtil.obj2String(result));
        }else {
            super.commence(request,response,e);
        }

    }
}
