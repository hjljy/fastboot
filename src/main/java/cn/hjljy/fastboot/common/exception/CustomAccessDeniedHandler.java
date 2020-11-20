package cn.hjljy.fastboot.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义访问无权限资源时的异常
 * @author xz
 * @date 2020/6/16
 */
@Component
public class CustomAccessDeniedHandler extends OAuth2AccessDeniedHandler {
    private final Logger logger = LoggerFactory.getLogger(cn.hjljy.fastboot.common.exception.CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException {
        logger.info("权限不足，请联系管理员 请求url = {}", request.getRequestURI());

        String msg = authException.getMessage();
        Map<String, Object> result = new HashMap<String, Object>(4){{
            put("msg", "您没有权限！");
            put("code", 403);
        }};
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(result);
    }
}
