package cn.hjljy.fastboot.autoconfig.exception;

import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjljy
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
    public ResultInfo requestMethodNoSuch(HttpRequestMethodNotSupportedException ex) {
        return ResultInfo.error(ResultCode.REQUEST_METHOD_EXCEPTION.getCode(), ex.getMessage());
    }


    /**
     * 处理oauth2登录验证异常
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResultInfo errorHandler(HttpServletResponse response, AuthenticationException ex) {
        //默认是用户密码不正确
        ResultInfo resultInfo = ResultInfo.error(ResultCode.USER_PASSWORD_WRONG.getCode(), ex.getMessage());
        Throwable cause = ex.getCause();
        //token 过期处理
        if (cause instanceof InvalidTokenException) {
            resultInfo = ResultInfo.error(ResultCode.TOKEN_EXPIRED);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        // 其余非法请求 未携带Token
        if (ex instanceof InsufficientAuthenticationException) {
            resultInfo = ResultInfo.error(ResultCode.TOKEN_NOT_FOUND);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return resultInfo;
    }

    /**
     * 处理参数类型异常信息
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultInfo errorHandler(IllegalArgumentException ex) {
        return ResultInfo.error(ResultCode.PARAMETERS_EXCEPTION.getCode(), ex.getMessage());
    }
    /**
     * 处理权限不足异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResultInfo errorHandler(AccessDeniedException ex) {
        ex.printStackTrace();
        return ResultInfo.error(ResultCode.PERMISSION_DENIED);
    }

    /**
     * 处理方法参数数据不符合要求异常信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultInfo errorHandler(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        ResultInfo resultInfo = ResultInfo.error(ResultCode.PARAMETERS_EXCEPTION);
        List<ObjectError> errors = result.getAllErrors();
        List<String> msg = new ArrayList<>();
        for (ObjectError error : errors) {
            msg.add(error.getDefaultMessage());
        }
        resultInfo.setMsg(msg.toString());
        return resultInfo;
    }

    /**
     * 处理业务抛出的异常信息
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResultInfo errorHandler(BusinessException ex) {
        return ResultInfo.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理其他的所有异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public ResultInfo errorHandler(Exception ex) {
        ex.printStackTrace();
        ResultInfo resultInfo = ResultInfo.error(ResultCode.ERROR);
        if (ex instanceof SQLException) {
            resultInfo.setCode(ResultCode.SQL_EXCEPTION.getCode());
        } else if (ex instanceof NullPointerException) {
            resultInfo.setCode(ResultCode.NPE_EXCEPTION.getCode());
        }
        return resultInfo;
    }
}