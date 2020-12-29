package cn.hjljy.fastboot.autoconfig.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
            String contentType = httpServletRequest.getContentType();
            //如果是POST请求并且不是文件上传
            if(HttpMethod.POST.name().equals(method)&&!contentType.equals(MediaType.MULTIPART_FORM_DATA_VALUE)){
                //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
                requestWrapper = new RequestReaderHttpServletRequestWrapper((HttpServletRequest) request);
            }

        }

        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        System.out.println("init");
    }
}
