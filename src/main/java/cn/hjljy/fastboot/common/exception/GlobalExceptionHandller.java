package cn.hjljy.fastboot.common.exception;

import cn.hjljy.fastboot.common.response.AjaxResult;
import cn.hjljy.fastboot.common.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @author yichaofan
 * @date 2020/6/5 18:04
 * @apiNote 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandller {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandller.class);

    @Value("${prod}")
    private Boolean prod;

    @ExceptionHandler(value = Exception.class)
    public AjaxResult errorHandler(HttpServletRequest request, Exception ex) {
        AjaxResult ajaxResult = AjaxResult.error("服务器开了个小差");
        dealErrorMessage(ex,ajaxResult);
        return ajaxResult;
    }

    @ExceptionHandler(value = CustomException.class)
    public AjaxResult errorHandler(HttpServletRequest request, CustomException ex) {
        AjaxResult ajaxResult = AjaxResult.error(ex.getCode(),ex.getMessage());
        dealErrorMessage(ex,ajaxResult);
        return ajaxResult;
    }

    @ExceptionHandler(value = NullPointerException.class)
    public AjaxResult errorHandler(HttpServletRequest request, NullPointerException ex) {
        AjaxResult ajaxResult = AjaxResult.error("服务器开了个小差");
        ajaxResult.setCode(ResultCode.NPE_EXCEPTION.getCode());
        dealErrorMessage(ex,ajaxResult);
        return ajaxResult;
    }

    private void dealErrorMessage(Exception ex, AjaxResult ajaxResult){
        StringWriter var1 = new StringWriter();
        PrintWriter var2 = new PrintWriter(var1);
        ex.printStackTrace(var2);
        String error = var1.toString();
        StringBuilder data = new StringBuilder();
        if (!prod) {
            ajaxResult.setData(data);
        }
        data.append(error);
        logger.error("{}", data);
    }
}
