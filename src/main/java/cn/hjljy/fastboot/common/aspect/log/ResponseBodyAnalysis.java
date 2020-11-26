package cn.hjljy.fastboot.common.aspect.log;

import cn.hjljy.fastboot.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author yichaofan
 * @since  2020/11/20 17:47
 * @apiNote 统一打印请求参数和返回参数日志
 */
@ControllerAdvice
@Slf4j
public class ResponseBodyAnalysis implements ResponseBodyAdvice {
    /**
     * 参数返回给前端之前进行，可以进行相关的处理例如，记录请求参数，响应参数，封装响应参数，等操作
     *
     * 源代码注释如下：
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     * @param body the body to be written
     * @param returnType the return type of the controller method
     * @param selectedContentType the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request the current request
     * @param response the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("\n请求访问地址：{}");
            sb.append("\n请求方式：{}");
            sb.append("\n请求body参数：{}");
            sb.append("\n请求token: {}");
            sb.append("\n返回参数：{}");
            if(request.getURI().toASCIIString().indexOf("File/upload")>0){
                log.info(sb.toString(),request.getURI(),request.getMethod().name(),"","", JacksonUtil.obj2String(body));
            }else{
                HttpHeaders headers = request.getHeaders();
                String token = "";
                if(headers != null && headers.size() > 0){
                    Object res = headers.get("Authorization");
                    if(res ==null){
                        token = "无";
                    }else{
                        token = res.toString();
                    }
                }
                log.info(sb.toString(),request.getURI(),request.getMethod().name(),inputStream2String(request.getBody()),token, JacksonUtil.obj2String(body));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return body;
    }

    /**
     * 如果要beforeBodyWrite方法生效，必须返回true
     * @param arg0
     * @param arg1
     * @return
     */
    @Override
    public boolean supports(MethodParameter arg0, Class arg1) {
        return true;
    }

    /**
     * 将inputStream里的数据读取出来并转换成字符串
     *
     * @param inputStream inputStream
     * @return String
     */
    private String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return sb.toString();
    }
}