package cn.hjljy.fastboot;


import cn.hjljy.fastboot.pojo.demo.po.DemoPo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yichaofan
 * @apiNote
 * @since 2020年06月17日 10:29:00
 */


public class FastMainTest {

    public static void main(String[] args) {
        BigDecimal one = BigDecimal.ONE;
        BigDecimal decimal = new BigDecimal("1234156.121654");
        one.add(BigDecimal.ONE);
        System.out.println(one.toString());
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
