package cn.hjljy.fastboot.autoconfig.filter;

import cn.hjljy.fastboot.common.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * @author hjljy
 */
@Slf4j
public class RequestBodyFilter implements Filter {

    private static final Integer TIMEOUT = 2;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        HttpServletRequest httpServletRequest = null;
        Instant start = Instant.now();
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            httpServletRequest = (HttpServletRequest) request;
            String method = httpServletRequest.getMethod();
            String contentType = httpServletRequest.getContentType() == null ? "" : httpServletRequest.getContentType();
            if(!contentType.equals(MediaType.MULTIPART_FORM_DATA_VALUE)){
                if (HttpMethod.POST.name().equals(method) || HttpMethod.PUT.name().equals(method)) {
                    //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
                    requestWrapper = new RequestReaderHttpServletRequestWrapper((HttpServletRequest) request);
                }
            }
        }

        // 在chain.doFiler方法中传递新的request对象
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
        if(httpServletRequest!=null){
            Duration duration = Duration.between(start, Instant.now());
            if (duration.getSeconds() > TIMEOUT) {
                log.error("请求结束,请求路径:{},请求ip:{},耗时:{}", httpServletRequest.getRequestURI(), RequestUtil.getRemoteHost(httpServletRequest), duration);
            }
        }

    }

    @Override
    public void init(FilterConfig arg0) {
        System.out.println("init");
    }
}
