package cn.hjljy.fastboot.common.exception;

import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
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
    public ResultInfo<Object> requestMethodNoSuch(HttpRequestMethodNotSupportedException ex) {
        ex.printStackTrace();
        return ResultInfo.error(ResultCode.REQUEST_METHOD_EXCEPTION.getCode(), ex.getMessage());
    }

    /**
     * 处理security登录验证异常
     */
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResultInfo<Object> errorHandler(BadCredentialsException ex) {
        ex.printStackTrace();
        return ResultInfo.error(ResultCode.USER_PASSWORD_WRONG.getCode(), ex.getMessage());
    }

    /**
     * 处理oauth2登录验证异常
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResultInfo<Object> errorHandler(HttpServletResponse response, AuthenticationException ex) {
        ex.printStackTrace();
        //默认是用户密码不正确
        ResultInfo<Object> resultInfo = ResultInfo.error(ResultCode.USER_PASSWORD_WRONG.getCode(), ex.getMessage());
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
     * 处理oauth2登录验证异常
     */
    @ExceptionHandler(value = InvalidGrantException.class)
    public ResultInfo<Object> errorHandler(InvalidGrantException ex) {
        ex.printStackTrace();
        return ResultInfo.error(ResultCode.USER_PASSWORD_WRONG.getCode(), ex.getMessage());
    }

    /**
     * 处理参数类型异常信息
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultInfo<Object> errorHandler(IllegalArgumentException ex) {
        return ResultInfo.error(ResultCode.PARAMETERS_EXCEPTION.getCode(), ex.getMessage());
    }

    /**
     * 处理参数数据格式异常信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultInfo<Object> errorHandler(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        ResultInfo<Object> resultInfo = ResultInfo.error(ResultCode.PARAMETERS_EXCEPTION);
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
    public ResultInfo<Object> errorHandler(BusinessException ex) {
        return ResultInfo.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理其他的所有异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public ResultInfo<Object> errorHandler(Exception ex) {
        ex.printStackTrace();
        ResultInfo<Object> resultInfo = ResultInfo.error(ResultCode.ERROR);
        if (ex instanceof AccessDeniedException) {
            resultInfo = ResultInfo.error(ResultCode.PERMISSION_DENIED);
        } else if (ex instanceof SQLException || ex instanceof BadSqlGrammarException || ex instanceof DataIntegrityViolationException) {
            resultInfo = ResultInfo.error(ResultCode.SQL_EXCEPTION);
        } else if (ex instanceof NullPointerException) {
            resultInfo = ResultInfo.error(ResultCode.NPE_EXCEPTION);
        } else if (ex instanceof MismatchedInputException || ex instanceof HttpMessageNotReadableException) {
            resultInfo = ResultInfo.error(ResultCode.PARAMETERS_EXCEPTION);
        }
        return resultInfo;
    }
}
