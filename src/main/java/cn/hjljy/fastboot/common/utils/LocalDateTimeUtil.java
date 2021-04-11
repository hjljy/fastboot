package cn.hjljy.fastboot.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * LocalDateTime 时间类常用方法
 *
 * @author hjljy
 */
public class LocalDateTimeUtil {
    /**
     * 默认时区
     */
    public static ZoneOffset defaultZone = ZoneOffset.of("+8");
    /**
     * 默认时间格式
     */
    public static String defaultFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取到毫秒级时间戳
     *
     * @param localDateTime 具体时间
     * @return long 毫秒级时间戳
     */
    public static long toEpochMilli(LocalDateTime localDateTime) {
        return toEpochMilli(localDateTime, defaultZone);
    }


    /**
     * 获取到毫秒级时间戳
     *
     * @param localDateTime 具体时间
     * @param zoneOffset    时区信息
     * @return long 毫秒级时间戳
     */
    public static long toEpochMilli(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return localDateTime.toInstant(zoneOffset).toEpochMilli();
    }

    /**
     * 毫秒级时间戳转 LocalDateTime
     *
     * @param epochMilli 毫秒级时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime ofEpochMilli(long epochMilli) {
        return ofEpochMilli(epochMilli, defaultZone);
    }

    /**
     * 毫秒级时间戳转 LocalDateTime
     *
     * @param epochMilli 毫秒级时间戳
     * @param zoneOffset 时区信息
     * @return LocalDateTime
     */
    public static LocalDateTime ofEpochMilli(long epochMilli, ZoneOffset zoneOffset) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), zoneOffset);
    }

    /**
     * 获取到秒级时间戳
     *
     * @param localDateTime 具体时间
     * @return long 秒级时间戳
     */
    public static long toEpochSecond(LocalDateTime localDateTime) {
        return toEpochSecond(localDateTime, defaultZone);
    }

    /**
     * 获取到秒级时间戳
     *
     * @param localDateTime 具体时间
     * @param zoneOffset    时区信息
     * @return long 秒级时间戳
     */
    public static long toEpochSecond(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return localDateTime.toEpochSecond(zoneOffset);
    }

    /**
     * 秒级时间戳转 LocalDateTime 默认+8时区
     *
     * @param epochSecond 秒级时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime ofEpochSecond(long epochSecond) {
        return ofEpochSecond(epochSecond, defaultZone);
    }

    /**
     * 秒级时间戳转 LocalDateTime
     *
     * @param epochSecond 秒级时间戳
     * @param zoneOffset  时区信息
     * @return LocalDateTime
     */
    public static LocalDateTime ofEpochSecond(long epochSecond, ZoneOffset zoneOffset) {
        return LocalDateTime.ofEpochSecond(epochSecond, 0, zoneOffset);
    }

    /**
     * Date时间类转LocalDateTime
     *
     * @param date Date时间类
     * @return LocalDateTime
     */
    public static LocalDateTime ofDate(Date date) {
        return ofDate(date, defaultZone);
    }

    /**
     * Date时间类转LocalDateTime
     *
     * @param date       Date时间类
     * @param zoneOffset 时区信息
     * @return LocalDateTime
     */
    public static LocalDateTime ofDate(Date date, ZoneOffset zoneOffset) {
        return date.toInstant().atOffset(zoneOffset).toLocalDateTime();
    }

    /**
     * LocalDateTime时间类转 Date时间类
     *
     * @param localDateTime LocalDateTime时间类
     * @return Date时间类
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return toDate(localDateTime, defaultZone);
    }

    /**
     * LocalDateTime时间类转 Date时间类
     *
     * @param localDateTime LocalDateTime时间类
     * @param zoneOffset    时区信息
     * @return Date时间类
     */
    public static Date toDate(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return Date.from(localDateTime.atZone(zoneOffset).toInstant());
    }

    /**
     * LocalDateTime转时间格式字符串
     *
     * @param localDateTime 时间
     * @return string
     */
    public static String formatToString(LocalDateTime localDateTime) {
        return formatToString(localDateTime, defaultFormat);
    }

    /**
     * LocalDateTime转时间格式字符串
     *
     * @param localDateTime 时间
     * @param format        时间格式
     * @return string
     */
    public static String formatToString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 时间字符串 转LocalDateTime
     *
     * @param localDateTime 时间字符串
     * @return LocalDateTime
     */
    public static LocalDateTime stringToFormat(String localDateTime) {
        return stringToFormat(localDateTime, defaultFormat);
    }

    /**
     * 时间字符串 转LocalDateTime
     *
     * @param localDateTime 时间字符串
     * @param format        时间格式
     * @return LocalDateTime
     */
    public static LocalDateTime stringToFormat(String localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(localDateTime, dateTimeFormatter);
    }
}
