package cn.hjljy.fastboot.common.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yichaofan
 * @apiNote : 路径映射错误处理 主要处理请求404 500 等情况返回的界面
 * @date 2020/6/7 18:04
 **/
@Controller
public class MainSiteErrorHandler implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public String handleError(HttpServletResponse response) {
        // 获取到响应状态码
        int status = response.getStatus();
        if (status == 404) {
            return "redirect:/404.html";
        } else {
            return "redirect:/500.html";
        }
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
