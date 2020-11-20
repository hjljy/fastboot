package cn.hjljy.fastboot.common.exception;

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
 * @author xz
 * @Description 针对Token过期异常处理
 * @date 2020/6/3 13:52
 **/
public class GlobalOauth2ExceptionTranslator implements WebResponseExceptionTranslator {
    private Logger log = LoggerFactory.getLogger(cn.hjljy.fastboot.common.exception.GlobalOauth2ExceptionTranslator.class);

    @Override
    public ResponseEntity translate(Exception e) {
        e.printStackTrace();
        // 得到真实的异常，传入的异常可能被过滤器链包装过
        Throwable t = e.getCause();
        // Token 无效的情况
        if (t instanceof InvalidTokenException) {
            log.error("携带的Token无效");
            Map<String, Object> data = new HashMap<String, Object>(4){{
               put("message", "您的会话已过期, 请重新登录！");
               put("responseCode", 403);
            }};
            return new ResponseEntity(data, HttpStatus.FORBIDDEN);
        }
        // 其余非法请求 未携带Token
        if (e instanceof InsufficientAuthenticationException) {
            log.error("未携带Token,非法请求！");
            Map<String, Object> data = new HashMap<String, Object>(4){{
                put("message", "非法请求！");
                put("responseCode", 401);
            }};
            return new ResponseEntity(data, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
