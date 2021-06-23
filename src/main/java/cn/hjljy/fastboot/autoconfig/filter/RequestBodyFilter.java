package cn.hjljy.fastboot.autoconfig.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description: 和RequestReaderHttpServletRequestWrapper一起共同处理request对象当中流数据只能读取一次的问题
 * @author hjljy
 */
public class RequestBodyFilter implements Filter {
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            String method = httpServletRequest.getMethod();
            String contentType = httpServletRequest.getContentType()==null?"":httpServletRequest.getContentType();
            if(HttpMethod.POST.name().equals(method)&&!contentType.equals(MediaType.MULTIPART_FORM_DATA_VALUE)){
                requestWrapper = new RequestReaderHttpServletRequestWrapper((HttpServletRequest) request);
            }
        }
        //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
        // 在chain.doFiler方法中传递新的request对象
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) {
        System.out.println("init");
    }
}
