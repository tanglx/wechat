package com.tanglx.wechat.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-05
 */
public class LocalDateUtil {

    /**
     * 日期格式
     */
    public interface Pattern {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String HH_MM = "HH:mm";
        String YYYY = "yyyy";
        String YYYYMMDD = "yyyyMMdd";
        String YYYYMM = "yyyyMM";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YYYY_DOT_MM_DOT_DD = "yyyy.MM.dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param dateTime 日期时间
     * @return Date
     */
    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定格式的指定时间
     *
     * @param dateTime 日期时间
     * @param pattern  指定格式
     * @return 指定格式的日期时间字符串
     */
    public static String formatTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取指定格式的指定时间
     *
     * @param dateTime 日期时间
     * @return 指定格式的日期时间字符串
     */
    public static String formatTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(Pattern.YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 获取指定格式的当前时间
     *
     * @param pattern 指定格式
     * @return 指定格式的当前时间日期字符串
     */
    public static String formatNow(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 判断dateTime1是否早于dateTime2
     *
     * @param dateTime1 日期时间1
     * @param dateTime2 日期时间2
     * @return 判断结果
     */
    public static boolean isBefore(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isBefore(dateTime2);
    }

    /**
     * 判断dateTime1与dateTime2是否是同一时间
     *
     * @param dateTime1 日期时间1
     * @param dateTime2 日期时间2
     * @return 判断结果
     */
    public static boolean isEqual(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isEqual(dateTime2);
    }

    /**
     * 判断dateTime1是否晚于dateTime2
     *
     * @param dateTime1 日期时间1
     * @param dateTime2 日期时间2
     * @return 判断结果
     */
    public static boolean isAfter(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isAfter(dateTime2);
    }

    /**
     * 获取指定日期的毫秒
     *
     * @param dateTime 日期时间
     * @return 毫秒
     */
    public static Long getMilliByTime(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的秒
     *
     * @param dateTime 日期时间
     * @return 秒
     */
    public static Long getSecondsByTime(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 日期加上一个数，根据field不同加不同值
     *
     * @param dateTime 日期时间
     * @param number   数值
     * @param field    单位
     * @return 已添加数值后的日期时间
     */
    public static LocalDateTime plus(LocalDateTime dateTime, long number, ChronoUnit field) {
        return dateTime.plus(number, field);
    }

    /**
     * 日期减去一个数，根据field不同减不同值
     *
     * @param dateTime 日期时间
     * @param number   数值
     * @param field    单位
     * @return 已减少数值后的日期时间
     */
    public static LocalDateTime minus(LocalDateTime dateTime, long number, ChronoUnit field) {
        return dateTime.minus(number, field);
    }

    /**
     * 获取两个日期的差
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param field         单位
     * @return 两个日期之间的差值
     */
    public static long between(LocalDateTime startDateTime, LocalDateTime endDateTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startDateTime), LocalDate.from(endDateTime));
        if (field == ChronoUnit.YEARS)
            return period.getYears();
        if (field == ChronoUnit.MONTHS)
            return period.getYears() * 12 + period.getMonths();
        return field.between(startDateTime, endDateTime);
    }

    /**
     * 获取某天的开始时间，例如：yyyy,MM,dd 00:00
     *
     * @param dateTime 某天的日期时间
     * @return 某天的开始时间
     */
    public static LocalDateTime getDayStart(LocalDateTime dateTime) {
        return dateTime.with(LocalTime.MIN);
    }

    /**
     * 获取某天的结束时间，例如：yyy,MM,dd 23:59:59
     *
     * @param dateTime 某天的日期时间
     * @return 某天的结束时间
     */
    public static LocalDateTime getDayEnd(LocalDateTime dateTime) {
        return dateTime.with(LocalTime.MAX);
    }

    /**
     * 获取某月第一周的开始时间
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime getFirstMondayByMonth(LocalDateTime dateTime) {
        LocalDateTime firstDay = dateTime.with(TemporalAdjusters.firstDayOfMonth());
        if (dateTime.getMonth().getValue() != firstDay.with(DayOfWeek.MONDAY).getMonth().getValue()) {
            firstDay = firstDay.with(DayOfWeek.MONDAY).plusWeeks(1);
        }
        return firstDay.with(LocalTime.MIN);
    }

    /**
     * 获取某月第一周的结束时间
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime getFirstSundayByMonth(LocalDateTime dateTime) {
        return getFirstMondayByMonth(dateTime).with(DayOfWeek.SUNDAY).with(LocalTime.MAX);
    }


    /**
     * 获取某月开始时间
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime getFirstDayByMonth(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 获取某月结束时间
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime getEndDayByMonth(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    /**
     * LocalDate转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(LocalDate date) {
        return getFirstSundayByMonth(LocalDateTime.of(date, LocalTime.MIN));
    }

    /**
     * 当前季度开始时间
     *
     * @return
     */
    public static LocalDateTime getQuarterStartDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.with(localDateTime.getMonth().firstMonthOfQuarter())
                .with(TemporalAdjusters.firstDayOfMonth())
                .with(LocalTime.MIN);
    }

    /**
     * 当前季度结束时间
     *
     * @return
     */
    public static LocalDateTime getQuarterEndDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.with(localDateTime.getMonth().firstMonthOfQuarter().plus(2))
                .with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);
    }

    /**
     * 当前年开始时间
     *
     * @return
     */
    public static LocalDateTime getYearStartDate() {
        return LocalDateTime.MIN.withYear(LocalDateTime.now().getYear());
    }

    /**
     * 当前年结束时间
     *
     * @return
     */
    public static LocalDateTime getYearEndDate() {
        return LocalDateTime.MAX.withYear(LocalDateTime.now().getYear());
    }

    /**
     * 当前月开始时间
     *
     * @return
     */
    public static LocalDateTime getMonthStartDate() {
        return LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 当前月结束时间
     *
     * @return
     */
    public static LocalDateTime getMonthEndDate() {
        return LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MIN);
    }
}
