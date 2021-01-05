package cn.hjljy.fastboot.common.exception;

import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yichaofan
 * @date 2020/6/5 18:04
 * @apiNote 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理请求方法不匹配异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultInfo requestMethodNoSuch(HttpServletResponse response, HttpRequestMethodNotSupportedException ex){
        ex.printStackTrace();
        ResultInfo resultInfo = ResultInfo.error(ResultCode.REQUEST_METHOD_EXCEPTION.getCode(),ex.getMessage());
        return resultInfo;
    }

    /**
     * 处理security登录验证异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResultInfo errorHandler(HttpServletRequest request, BadCredentialsException ex) {
        ex.printStackTrace();
        ResultInfo resultInfo = ResultInfo.error(ResultCode.USER_PASSWORD_WRONG.getCode(),ex.getMessage());
        return resultInfo;
    }

    /**
     * 处理oauth2登录验证异常
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResultInfo errorHandler(HttpServletResponse response, AuthenticationException ex) {
        ex.printStackTrace();
        //默认是用户密码不正确
        ResultInfo resultInfo = ResultInfo.error(ResultCode.USER_NOT_FOUND_OR_ENABLE.getCode(),ex.getMessage());
        Throwable cause = ex.getCause();
        //token 过期处理
        if (cause instanceof InvalidTokenException) {
            resultInfo= ResultInfo.error(ResultCode.TOKEN_EXPIRED);
        }
        // 其余非法请求 未携带Token
        if (ex instanceof InsufficientAuthenticationException) {
            resultInfo=  ResultInfo.error(ResultCode.TOKEN_NOT_FOUND);
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return resultInfo;
    }

    /**
     * 处理参数异常信息
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultInfo errorHandler(HttpServletRequest request, IllegalArgumentException ex) {
        ResultInfo resultInfo = ResultInfo.error(ResultCode.PARAMETERS_EXCEPTION.getCode(),ex.getMessage());
        return resultInfo;
    }

    /**
     * 处理业务抛出的异常信息
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResultInfo errorHandler(HttpServletRequest request, BusinessException ex) {
        ResultInfo resultInfo = ResultInfo.error(ex.getCode(),ex.getMessage());
        return resultInfo;
    }

    /**
     * 处理其他的所有异常信息
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResultInfo errorHandler(HttpServletRequest request,HttpServletResponse response, Exception ex) {
        ex.printStackTrace();
        ResultInfo resultInfo = ResultInfo.error(ResultCode.ERROR);
        if(ex instanceof AccessDeniedException ){
            resultInfo=ResultInfo.error(ResultCode.PERMISSION_DENIED);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }else if(ex instanceof SQLException){
            resultInfo.setCode(ResultCode.SQL_EXCEPTION.getCode());
        }else if(ex instanceof NullPointerException){
            resultInfo.setCode(ResultCode.NPE_EXCEPTION.getCode());
        }
        return resultInfo;
    }
}
