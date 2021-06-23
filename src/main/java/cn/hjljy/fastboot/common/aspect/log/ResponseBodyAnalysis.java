package cn.hjljy.fastboot.common.aspect.log;

import cn.hjljy.fastboot.common.utils.JacksonUtil;
import cn.hjljy.fastboot.common.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author hjljy
 * @apiNote 统一打印请求参数和返回参数日志
 * @since 2020/11/20 17:47
 */
@ControllerAdvice
@Slf4j
public class ResponseBodyAnalysis implements ResponseBodyAdvice<Object> {
    /**
     * 参数返回给前端之前进行，可以进行相关的处理例如，记录请求参数，响应参数，封装响应参数，等操作
     * <p>
     * 源代码注释如下：
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter returnType,
                                  @NotNull MediaType selectedContentType, @NotNull Class selectedConverterType, @NotNull ServerHttpRequest request,
                                  @NotNull ServerHttpResponse response) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("\n请求访问地址：{}");
            sb.append("\n请求方式：{}");
            sb.append("\n请求body参数：{}");
            sb.append("\n请求token: {}");
            sb.append("\n返回参数：{}");
            HttpHeaders headers = request.getHeaders();
            MediaType contentType = headers.getContentType();
            if (contentType != null && contentType.getType().equals(MediaType.MULTIPART_FORM_DATA.getType())) {
                log.info(sb.toString(), request.getURI(), Objects.requireNonNull(request.getMethod()).name(), "", "", JacksonUtil.obj2String(body));
            } else {
                String token;
                Object res = headers.get("Authorization");
                if (res == null) {
                    token = "无";
                } else {
                    token = res.toString();
                }
                log.info(sb.toString(), request.getURI(), Objects.requireNonNull(request.getMethod()).name(), RequestUtil.inputStream2String(request.getBody()), token, JacksonUtil.obj2String(body));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return body;
    }

    /**
     * 如果要beforeBodyWrite方法生效，必须返回true
     */
    @Override
    public boolean supports(@NotNull MethodParameter arg0, @NotNull Class arg1) {
        return true;
    }
}
