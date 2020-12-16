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
        Map<String,Object> map =new HashMap<>();
        map.put("iotPush","true");

        Set<String> set =new HashSet<>();
        set.add("{sys:menu:add}");
        boolean contains = set.contains("sys:menu:add");
        System.out.println(map.get("iotPush").equals("true"));
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
