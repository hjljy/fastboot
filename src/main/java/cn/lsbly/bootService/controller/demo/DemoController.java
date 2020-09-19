package cn.lsbly.bootService.controller.demo;



import cn.lsbly.bootService.autoconfig.security.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.lsbly.cn）
 * @since 2020-07-03
 */
@RestController
@RequestMapping("/demo-po")
public class DemoController {

    @GetMapping
    public String getDemo(String name){
        if(SecurityContextHolder.getContext() == null) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

//        return ResultInfo.success(userInfo);
        return userInfo.toString();
    }
}

