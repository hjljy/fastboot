package cn.hjljy.fastboot.common.exception;

import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
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
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResultInfo errorHandler(HttpServletRequest request, AuthenticationException ex) {
        ex.printStackTrace();
        ResultInfo resultInfo = ResultInfo.error(ResultCode.USER_NOT_FOUND_OR_ENABLE.getCode(),ex.getMessage());
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
    public ResultInfo errorHandler(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        ResultInfo resultInfo = ResultInfo.error(ResultCode.ERROR);
        return resultInfo;
    }
}
