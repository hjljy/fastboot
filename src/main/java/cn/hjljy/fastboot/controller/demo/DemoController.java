package cn.hjljy.fastboot.controller.demo;



import org.springframework.web.bind.annotation.*;

import cn.hjljy.fastboot.pojo.dto.DemoPoDto;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-07-03
 */
@RestController
@RequestMapping("/demo-po")
public class DemoController {

    @GetMapping
    public String getDemo(DemoPoDto demoPoDto){
        return "123456";
    }
}

