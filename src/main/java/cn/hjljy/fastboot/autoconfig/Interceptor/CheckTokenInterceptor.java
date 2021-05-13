package cn.hjljy.fastboot.autoconfig.Interceptor;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import cn.hjljy.fastboot.autoconfig.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.common.utils.JacksonUtil;
import cn.hjljy.fastboot.common.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author hjljy
 * 全局check_token
 * 暂时无需使用
 */
@Deprecated
@Component
@Slf4j
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Autowired
    FastBootConfig fastBootConfig;

    @Autowired
    RestTemplate restTemplate;

    @Value("${fastboot.oauth2.checkTokenUrl}")
    String checkTokenUrl;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String token = RequestUtil.getToken(request);

        ResultInfo<?> entity = restTemplate.getForObject(checkTokenUrl + token, ResultInfo.class);
        if (null == entity) {
            throw new BusinessException(ResultCode.DEFAULT, "服务器繁忙,请稍后尝试");
        }
        if (ResultCode.SUCCESS.getCode() == entity.getCode()) {
            return true;
        }
        String result = JacksonUtil.obj2String(entity);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
        writer.close();
        return false;
    }
}
