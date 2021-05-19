package cn.hjljy.fastboot.autoconfig.exception;

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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        return ResultInfo.error(ResultCode.REQUEST_METHOD_EXCEPTION, ex.getMessage());
    }

    /**
     * 处理oauth2登录验证异常
     */
    @ExceptionHandler(value = InsufficientAuthenticationException.class)
    public ResultInfo<Object> errorHandler(InsufficientAuthenticationException ex) {
        return ResultInfo.error(ResultCode.TOKEN_NOT_FOUND);
    }

    /**
     * 处理oauth2登录验证异常
     */
    @ExceptionHandler(value = InvalidTokenException.class)
    public ResultInfo<Object> errorHandler(InvalidTokenException ex) {
        ex.printStackTrace();
        return ResultInfo.error(ResultCode.TOKEN_EXPIRED, ex.getMessage());
    }

    /**
     * 处理oauth2登录验证异常
     */
    @ExceptionHandler(value = InvalidGrantException.class)
    public ResultInfo<Object> errorHandler(InvalidGrantException ex) {
        ex.printStackTrace();
        return ResultInfo.error(ResultCode.USER_PASSWORD_WRONG, ex.getMessage());
    }
    /**
     * 处理参数类型异常信息
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultInfo<Object> errorHandler(IllegalArgumentException ex) {
        return ResultInfo.error(ResultCode.PARAMETERS_EXCEPTION, ex.getMessage());
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
        } else if (ex instanceof MismatchedInputException || ex instanceof HttpMessageNotReadableException|| ex instanceof MethodArgumentTypeMismatchException) {
            resultInfo = ResultInfo.error(ResultCode.PARAMETERS_EXCEPTION);
        }
        return resultInfo;
    }
}
