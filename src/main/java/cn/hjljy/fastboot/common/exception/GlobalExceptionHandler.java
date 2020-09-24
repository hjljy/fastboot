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
import java.util.Map;

/**
 * @author yichaofan
 * @date 2020/6/5 18:04
 * @apiNote 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active}")
    private String prod;

    @ExceptionHandler(value = Exception.class)
    public ResultInfo errorHandler(HttpServletRequest request, Exception ex) {
        ResultInfo resultInfo = ResultInfo.error("服务器开了个小差");
        dealErrorMessage(request,ex, resultInfo);
        return resultInfo;
    }
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResultInfo errorHandler(HttpServletRequest request, BadCredentialsException ex) {
        ResultInfo resultInfo = ResultInfo.error(ex.hashCode(),ex.getMessage());
        dealErrorMessage(request,ex, resultInfo);
        return resultInfo;
    }


    @ExceptionHandler(value = BusinessException.class)
    public ResultInfo errorHandler(HttpServletRequest request, BusinessException ex) {
        ResultInfo resultInfo = ResultInfo.error(ex.getCode(),ex.getMessage());
        dealErrorMessage(request,ex, resultInfo);
        return resultInfo;
    }

    private void dealErrorMessage(HttpServletRequest request,Exception ex, ResultInfo resultInfo){
        StringWriter var1 = new StringWriter();
        PrintWriter var2 = new PrintWriter(var1);
        ex.printStackTrace(var2);
        String error = var1.toString();
        StringBuilder errorData = new StringBuilder();
        errorData.append(error);
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder parameterData =new StringBuilder();
        String body = ServletUtil.getBody(request);
        String clientIP = ServletUtil.getClientIP(request);
        for (String s : parameterMap.keySet()) {
            parameterData.append(parameterMap.get(s).toString());
        }
        if (!prod.equals("prod")) {
            resultInfo.setData(body);
        }
        log.error("请求路径地址：{}",request.getRequestURL());
        log.error("请求路径方式：{}",request.getMethod());
        log.error("请求参数信息：{}",body);
        log.error("请求来源地址：{}",clientIP);
        log.error("请求错误信息：{}", errorData);
    }
}
