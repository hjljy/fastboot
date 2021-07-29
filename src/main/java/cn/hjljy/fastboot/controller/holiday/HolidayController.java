package cn.hjljy.fastboot.controller.holiday;


import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hjljy.fastboot.common.utils.LocalDateTimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2021-07-27
 */
@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @GetMapping("/weekdays")
    public ResultInfo<List<LocalDate>> getAllDayOfWeek(){
        return ResultInfo.success(LocalDateTimeUtil.allDaysOfWeek(LocalDate.now()));
    }

}

