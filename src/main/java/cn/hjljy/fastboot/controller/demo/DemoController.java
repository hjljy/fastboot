package cn.hjljy.fastboot.controller.demo;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping
    public String getDemo(@RequestBody DemoPoDto demoPoDto){
        System.out.println(demoPoDto.toString());
        return "123456";
    }
}

