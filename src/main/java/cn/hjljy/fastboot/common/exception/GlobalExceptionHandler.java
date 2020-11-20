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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
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

    @ExceptionHandler(value = Exception.class)
    public ResultInfo errorHandler(HttpServletRequest request, Exception ex) {
        ResultInfo resultInfo = ResultInfo.error(ResultCode.ERROR);
        return resultInfo;
    }
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResultInfo errorHandler(HttpServletRequest request, BadCredentialsException ex) {
        ResultInfo resultInfo = ResultInfo.error(ResultCode.USER_PASSWORD_WRONG.getCode(),ex.getMessage());
        return resultInfo;
    }
    @ExceptionHandler(value = AuthenticationException.class)
    public ResultInfo errorHandler(HttpServletRequest request, AuthenticationException ex) {
        ResultInfo resultInfo = ResultInfo.error(ResultCode.USER_NOT_FOUND_OR_ENABLE.getCode(),ex.getMessage());
        return resultInfo;
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResultInfo errorHandler(HttpServletRequest request, BusinessException ex) {
        ResultInfo resultInfo = ResultInfo.error(ex.getCode(),ex.getMessage());
        return resultInfo;
    }

}
