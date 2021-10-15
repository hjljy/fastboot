package cn.hjljy.fastboot;


import cn.hjljy.fastboot.common.utils.JacksonUtil;
import cn.hjljy.fastboot.common.utils.LocalDateTimeUtil;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import org.apache.commons.codec.Charsets;
import org.springframework.security.authentication.BadCredentialsException;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author hjljy
 * @apiNote
 * @since 2020年06月17日 10:29:00
 */


public class FastMainTest {

    public static void main(String[] args) {
        List<LocalDate> dates = LocalDateTimeUtil.allDaysOfMonth(LocalDate.now());
        SysUser user = new SysUser();
        user.setCreateTime(LocalDateTime.now());
        String string = JacksonUtil.obj2String(dates);
        JacksonUtil.obj2String(user);
    }
    public static Date str2Date(String date, String f) {
        SimpleDateFormat format = new SimpleDateFormat(f);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date1 = null;
        try {
            date1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

}
