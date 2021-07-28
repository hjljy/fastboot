package cn.hjljy.fastboot;


import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.LocalDateTimeUtil;
import org.apache.commons.codec.Charsets;
import org.springframework.security.authentication.BadCredentialsException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author hjljy
 * @apiNote
 * @since 2020年06月17日 10:29:00
 */


public class FastMainTest {

    public static void main(String[] args) {
        Date date =new Date(190,8,8);
        ChineseDate chineseDate = new ChineseDate(date);
        System.out.println(chineseDate.toStringNormal());
    }

}
