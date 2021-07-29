package cn.hjljy.fastboot.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * LocalDateTime 时间类常用方法
 * @author 海加尔金鹰
 */
public class LocalDateTimeUtil {
    /**
     * 默认时区
     */
    public static ZoneOffset defaultZone = ZoneOffset.of("+8");
    /**
     * 默认时间格式
     */
    public static String defaultFormat = "yyyy:MM:dd HH:mm:ss";

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

    /**
     * 一周的第一天
     *
     * @param localDate 当地日期
     * @return {@link LocalDate}
     */
    public static LocalDate firstDayOfWeek(LocalDate localDate){
        return localDate.with(DayOfWeek.MONDAY);
    }

    /**
     * 一周的最后一天
     *
     * @param localDate 当地日期
     * @return {@link LocalDate}
     */
    public static LocalDate lastDayOfWeek(LocalDate localDate){
        return localDate.with(DayOfWeek.SUNDAY);
    }

    /**
     * 月的第一天
     *
     * @param localDate 当地日期
     * @return {@link LocalDate}
     */
    public static LocalDate firstDayOfMonth(LocalDate localDate){
        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 月的最后一天
     *
     * @param localDate 当地日期
     * @return {@link LocalDate}
     */
    public static LocalDate lastDayOfMonth(LocalDate localDate){
        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 每年的第一天
     *
     * @param localDate 当地日期
     * @return {@link LocalDate}
     */
    public static LocalDate firstDayOfYear(LocalDate localDate){
        return localDate.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 每年的最后一天
     *
     * @param localDate 当地日期
     * @return {@link LocalDate}
     */
    public static LocalDate lastDayOfYear(LocalDate localDate){
        return localDate.with(TemporalAdjusters.lastDayOfYear());
    }


    /**
     * 每周的所有日期  即周一到周日
     *
     * @param localDate 当地日期
     * @return {@link List<LocalDate>}
     */
    public static List<LocalDate> allDaysOfWeek(LocalDate localDate){
        List<LocalDate> allDays=new ArrayList<>();
        allDays.add(localDate.with(DayOfWeek.MONDAY));
        allDays.add(localDate.with(DayOfWeek.TUESDAY));
        allDays.add(localDate.with(DayOfWeek.WEDNESDAY));
        allDays.add(localDate.with(DayOfWeek.THURSDAY));
        allDays.add(localDate.with(DayOfWeek.FRIDAY));
        allDays.add(localDate.with(DayOfWeek.SATURDAY));
        allDays.add(localDate.with(DayOfWeek.SUNDAY));
        return allDays;
    }

    /**
     * 每月的所有日期  即1日到31日
     *
     * @param localDate 当地日期
     * @return {@link List<LocalDate>}
     */
    public static List<LocalDate> allDaysOfMonth(LocalDate localDate){
        List<LocalDate> allDays=new ArrayList<>();
        LocalDate firstDayOfMonth = firstDayOfMonth(localDate);
        LocalDate lastDayOfMonth = lastDayOfMonth(localDate);
        allDays.add(firstDayOfMonth);
        int i = 1;
        LocalDate temp = firstDayOfMonth;
        while (!temp.isEqual(lastDayOfMonth)){
            LocalDate day = firstDayOfMonth.plusDays(i);
            allDays.add(day);
            temp=day;
            i++;
        }
        return allDays;
    }
}
