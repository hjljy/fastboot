package cn.hjljy.fastboot.common.exception;

import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yichaofan
 * @apiNote  针对Token过期异常处理
 * @since  2020/6/16
 **/
public class GlobalOauth2ExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) {
        e.printStackTrace();
        // 得到真实的异常，传入的异常可能被过滤器链包装过
        Throwable t = e.getCause();
        // Token 无效的情况
        if (t instanceof InvalidTokenException) {
            return new ResponseEntity(ResultInfo.error(ResultCode.TOKEN_EXPIRED), HttpStatus.FORBIDDEN);
        }
        // 其余非法请求 未携带Token
        if (e instanceof InsufficientAuthenticationException) {
            return new ResponseEntity(ResultInfo.error(ResultCode.TOKEN_NOT_FOUND), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
