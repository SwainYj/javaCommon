package com.swain.common.utils.utils;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.joda.time.DateTime.now;

/**
 * 时间相关的工具类
 */
public final class TimeUtil {

    public static String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static String yyMMdd = "yyyy-MM-dd";
    public static String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static String MMdd = "MMdd";
    public static String HHmmss = "HH:mm:ss";

    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23,
            23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"1",
            "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "1"};

    private static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    /**
     * 计算星座
     *
     * @param birthday
     * @return
     */
    public static String constellationValue(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        return day < dayArr[month - 1] ? constellationArr[month - 1]
                : constellationArr[month];
    }

    /**
     * 计算年龄
     *
     * @param birthday
     * @return
     */
    public static int calculateAge(Date birthday) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (birthday != null) {
            now.setTime(new Date());
            born.setTime(birthday);
            if (born.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
        }
        return age;
    }

    /**
     * 获取分钟差
     *
     * @param lastTime
     * @param isAbs    true取绝对值，false 可能为负
     * @return
     */
    public static long getDiffMinutes(DateTime lastTime, boolean isAbs) {
        long minute = 0L;
        minute = (lastTime.getMillis() - System.currentTimeMillis()) / 1000 / 60;
        if (isAbs) {
            minute = Math.abs(minute);
        }
        return minute;
    }

    public static long getDiffMinutes(Date lastTime, boolean isAbs) {
        long minute = 0L;
        minute = (lastTime.getTime() - System.currentTimeMillis()) / 1000 / 60;
        if (isAbs) {
            minute = Math.abs(minute);
        }
        return minute;
    }

    public static long getDiffMinutes(DateTime lastTime) {
        return getDiffMinutes(lastTime, true);
    }

    public static long getDiffMinutes(Date lastTime) {
        return getDiffMinutes(lastTime, true);
    }

    /**
     * 两天间隔多少天
     *
     * @param firstDay
     * @param lastDay
     * @return
     */
    public static long getDiffBoth(DateTime firstDay, DateTime lastDay) {
        Duration duration = new Duration(firstDay, lastDay);
        return Math.abs(duration.getStandardDays());
    }

    /**
     * 相差几个月
     *
     * @param firstDay
     * @param lastDay
     * @return
     */
    public static int getDiffMonth(DateTime firstDay, DateTime lastDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDay.toDate());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(lastDay.toDate());
        int year1 = calendar.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);
        if (year1 == year2) {
            return Math.abs(calendar.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH));
        } else if (year1 < year2) {
            int dfM = Math.abs(year1 - year2) * 12;
            dfM = Math.abs(dfM - calendar.get(Calendar.MONTH) + calendar2.get(Calendar.MONTH));
            return dfM;
        } else {
            int dfM = Math.abs(year2 - year1) * 12;
            dfM = Math.abs(dfM - calendar2.get(Calendar.MONTH) + calendar.get(Calendar.MONTH));
            return dfM;
        }
    }

    /**
     * 几天后
     *
     * @param day
     * @return
     */
    public static DateTime nowAddDays(int day) {
        DateTime firstDay = DateTime.now();
        return firstDay.plusDays(day);
    }


    /**
     * 时间加上秒
     *
     * @param lastTime
     * @param seconds
     * @return
     */
    public static Date dayAddSeconds(Date lastTime, long seconds) {
        return new Date(lastTime.getTime() + seconds * 1000);
    }

    /**
     * 获取天数差
     *
     * @param lastDay
     * @return
     */
    public static long getDiffDays(DateTime lastDay) {
        if (lastDay == null) {
            return 0;
        }
        lastDay = DateTime.parse(lastDay.toString(yyMMdd));
        Duration duration = new Duration(lastDay, now());
        return Math.abs(duration.getStandardDays());
    }

    /**
     * 获取秒数差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDiffSeconds(long startDate, long endDate) {
        if (startDate == 0 || endDate == 0) {
            return 0;
        }
        int c = (int) ((endDate - startDate) / 1000);
        return c;
    }


    /**
     * 当天剩余时间
     *
     * @param millis true-毫秒 false-秒
     * @return 毫秒/秒
     */
    public static long timeTodayLeft(boolean millis) {
        DateTime dateTime = new DateTime().millisOfDay().withMaximumValue();
        if (millis) {
            return Math.max(1, new Duration(new DateTime(), dateTime).getMillis());
        } else {
            return Math.max(1, new Duration(new DateTime(), dateTime).getStandardSeconds());
        }
    }

    public static LocalDate dateToLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    /**
     * 距离明天有几秒
     *
     * @return
     */
    public static long secondsFromTomorrow() {
        DateTime nowTime = now();
        DateTime dateTime = nowTime.plusDays(1);
        dateTime = dateTime.minus(dateTime.millisOfDay().get());
        return secondsDuration(nowTime, dateTime);
    }

    /**
     * 距离明天有几秒
     * 增加半个小时的随机数
     *
     * @return
     */
    public static long secondsFromTomorrowRandom() {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(1);
        dateTime = dateTime.minus(dateTime.millisOfDay().get() - ThreadLocalRandom.current().nextInt(6000, 1800000));
        DateTime nowTime = now();
        return secondsDuration(nowTime, dateTime);
    }

    /**
     * 距离明天有几秒
     * 按自然日计算
     * 增加半个小时的随机数
     *
     * @return
     */
    public static long secondsFromTomorrowRandomZiRan() {
        Calendar curDate = Calendar.getInstance();
        Calendar date = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, new Random().nextInt(30), 0);
        return (date.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;

    }


    /**
     * 距离某天有几秒
     * 增加半个小时的随机数
     *
     * @param days 当天加减天数
     * @return
     */
    public static long secondsFromAnydayRandom(int days) {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(days);
        dateTime = dateTime.minus(dateTime.millisOfDay().get() - ThreadLocalRandom.current().nextInt(6000, 1800000));
        DateTime nowTime = now();
        return secondsDuration(nowTime, dateTime);
    }

    /**
     * 距离某天有几秒
     *
     * @param days 当天加减天数
     * @return
     */
    public static long secondsFromAnyday(int days) {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(days);
        dateTime = dateTime.minus(dateTime.millisOfDay().get());
        DateTime nowTime = now();
        return secondsDuration(nowTime, dateTime);
    }

    /**
     * 距离下周一有几秒
     *
     * @return
     */
    public static long secondsFromWeeks() {
        DateTime dateTime = now();
        int week = dateTime.getDayOfWeek();
        //下周一时间
        dateTime = dateTime.plusDays(8 - week);
        dateTime = dateTime.minus(dateTime.millisOfDay().get());
        DateTime nowTime = now();
        return secondsDuration(nowTime, dateTime);
    }

    /**
     * 距离现在间隔多少秒
     *
     * @return
     */
    public static long secondsDuration(DateTime dateTime) {
        DateTime now = now();
        Duration duration = new Duration(dateTime, now);
        return Math.abs(duration.getStandardSeconds());
    }

    /**
     * 两个时间点间隔多少秒
     *
     * @return
     */
    public static long secondsDuration(DateTime startTime, DateTime endTime) {
        Duration duration = new Duration(startTime, endTime);
        return Math.abs(duration.getStandardSeconds());
    }

    /**
     * 两个时间点（毫秒）间隔多少毫秒
     *
     * @return
     */
    public static long secondsDuration(Long startTime, Long endTime) {
        Duration duration = new Duration(startTime, endTime);
        return Math.abs(duration.getMillis());
    }

    /**
     * 距离现在间隔多少分钟
     *
     * @return
     */
    public static long minutesDuration(DateTime dateTime) {
        DateTime now = now();
        Duration duration = new Duration(dateTime, now);
        return Math.abs(duration.getStandardMinutes());
    }

    /**
     * 两个时间点间隔多少分钟
     *
     * @return
     */
    public static long minutesDuration(DateTime startTime, DateTime endTime) {
        DateTime start = new DateTime(startTime);
        DateTime end = new DateTime(endTime);
        Duration duration = new Duration(start, end);
        return Math.abs(duration.getStandardMinutes());
    }


    public static String formatDate(Date date, String pattern) {
        return new DateTime(date.getTime()).toString(pattern);
    }

    /**
     * 昨天的日期String
     *
     * @return String
     */
    public static String yesterday() {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(-1);
        dateTime = dateTime.minus(dateTime.millisOfDay().get());
        return dateTime.toString(yyMMdd);
    }

    /**
     * 昨天的日期时间戳 2019-08-21 00:00:00
     *
     * @return
     */
    public static Long yesterdayStamp() {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(-1);
        dateTime = dateTime.minus(dateTime.millisOfDay().get());
        return dateTime.minus(dateTime.millisOfDay().get()).getMillis();
    }

    /**
     * 今天的日期时间戳 2019-08-21 00:00:00
     *
     * @return
     */
    public static Long todayStamp() {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(0);
        dateTime = dateTime.minus(dateTime.millisOfDay().get());
        return dateTime.minus(dateTime.millisOfDay().get()).getMillis();
    }

    /**
     * 获取几天前 或几天后
     *
     * @param day
     * @return
     */
    public static Long getFixDay(Integer day) {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(day);
        dateTime = dateTime.minus(dateTime.millisOfDay().get());
        return dateTime.minus(dateTime.millisOfDay().get()).getMillis();
    }


    /**
     * 今天日期String
     *
     * @return String
     */
    public static String todayMD() {
        DateTime dateTime = now();
        return dateTime.toString(MMdd);
    }

    public static String today() {
        DateTime dateTime = now();
        return dateTime.toString(yyMMdd);
    }

    public static String todayAll() {
        DateTime dateTime = now();
        return dateTime.toString(yyyyMMddHHmmss);
    }


    public static String todayMinute() {
        DateTime dateTime = now();
        return String.format("%s:00", dateTime.toString(yyyyMMddHHmm));
    }


    /**
     * 某天日期String
     *
     * @param n 今天+-多少天
     * @return String
     */
    public static String day(int n) {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(n);
        return dateTime.toString(yyMMdd);
    }

    public static String day(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toString(yyMMdd);
    }

    public static String dayTime(int n) {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(n);
        return dateTime.toString(yyyyMMddHHmmss);
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static String addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toString(yyyyMMddHHmmss);
    }

    /**
     * 某天日期String
     *
     * @param n 今天+-多少天
     * @return String
     */
    public static Date day2(int n) {
        return parseDate2(day(n));
    }

    /**
     * 某天日期+-1
     *
     * @param date
     * @param n
     * @return
     */
    public static Date day3(Date date, int n) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, n);
        return calendar.getTime();
    }

    /**
     * 转换日期到天
     *
     * @return String
     */
    public static String toTime(Date date) {
        return formatDate(date, yyMMdd);
    }

    /**
     * 转换日期到天
     *
     * @return String
     */
    public static String toTime(Long time) {
        Date d = new Date();
        d.setTime(time);
        return formatDate(d, yyMMdd);
    }

    /**
     * 转换日期到天
     *
     * @return String
     */
    public static String toDateTime(Long date) {
        Date d = new Date();
        d.setTime(date);
        return formatDate(d, yyyyMMddHHmmss);
    }

    /**
     * 今天日期String
     *
     * @return String
     */
    public static String todayTime() {
        DateTime dateTime = now();
        return dateTime.toString(yyyyMMddHHmmss);
    }

    public static String dayTime(Date date) {
        return formatDate(date, yyyyMMddHHmmss);
    }

    /**
     * @param date
     * @return "yyyy-MM-dd"
     */
    public static String dayTime2(Date date) {
        return formatDate(date, yyMMdd);
    }

    /**
     * @param date
     * @return "HHmmss"
     */
    public static String dayTime3(Date date) {
        return formatDate(date, HHmmss);
    }

    /**
     * 今天日期String
     *
     * @return String
     */
    public static String logTime() {
        DateTime dateTime = now();
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间戳
     *
     * @return String
     */
    public static long timeNow() {
        return System.currentTimeMillis();
    }

    /**
     * 分钟向上取整
     *
     * @param num 每几分钟
     * @return
     */
    public static String minuteFloor(int num) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(yyyyMMddHHmm);
        if (num < 0 || num > 60) {
            return dateFormat.format(new Date()) + ":00";
        }
        Calendar calendar = Calendar.getInstance();
        int time = new Double(num * Math.floor(calendar.get(Calendar.MINUTE) / num)).intValue();
        calendar.set(Calendar.MINUTE, time);
        return dateFormat.format(calendar.getTime()) + ":00";
    }

    /**
     * 分钟向下取整
     *
     * @param num 每几分钟
     * @return
     */
    public static String minuteCeil(int num) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(yyyyMMddHHmm);
        if (num < 0 || num > 60) {
            return dateFormat.format(new Date()) + ":00";
        }
        Calendar calendar = Calendar.getInstance();
        int time = new Double(num * Math.ceil(calendar.get(Calendar.MINUTE) / num)).intValue();
        calendar.set(Calendar.MINUTE, time);
        return dateFormat.format(calendar.getTime()) + ":00";
    }

    /**
     * 比当前时间提前/延迟多少秒
     *
     * @param seconds 多少秒
     * @return long 时间戳
     */
    public static long delaySeconds(int seconds) {
        DateTime dateTime = now();
        dateTime = dateTime.plusSeconds(seconds);
        return dateTime.getMillis();
    }

    /**
     * 比某个时间提前/延迟多少秒
     *
     * @param dateTime
     * @param seconds
     * @return
     */
    public static long delaySeconds(DateTime dateTime, int seconds) {
        dateTime = dateTime.plusSeconds(seconds);
        return dateTime.getMillis();
    }

    /**
     * 比当前时间提前/延迟多少分钟
     *
     * @param minuter 多少分钟
     * @return DateTime 时间
     */
    public static DateTime delayTime(int minuter) {
        DateTime dateTime = now();
        dateTime = dateTime.plusMinutes(minuter);
        return dateTime;
    }

    /**
     * 比当前时间提前/延迟多少分钟
     *
     * @param minuter 多少分钟
     * @return long 时间戳
     */
    public static long delay(int minuter) {
        DateTime dateTime = now();
        dateTime = dateTime.plusMinutes(minuter);
        return dateTime.getMillis();
    }

    /**
     * 比当前时间提前/延迟多少天
     *
     * @param day 多少天
     * @return DateTime 时间
     */
    public static DateTime delayDay(int day) {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(day);
        return dateTime;
    }

    /**
     * 比当前时间提前/延迟多少天
     *
     * @param day 多少天
     * @return long 时间戳
     */
    public static long delayDay2Long(int day) {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(day);
        return dateTime.getMillis();
    }

    /**
     * 比当前时间提前/延迟多少天
     *
     * @param day 多少天
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String delayDay2String(int day) {
        DateTime dateTime = now();
        dateTime = dateTime.plusDays(day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        String date = sdf.format(dateTime.toDate());
        return date;
    }

    /**
     * 获取月的某天
     *
     * @param diffMonth 加减月
     */
    public static Date getDayOfMonth(int diffMonth, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + diffMonth);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 字符串转 日期
     *
     * @param source
     * @return
     */
    public static Date parseDate(String source, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(source);
        } catch (ParseException e) {
            logger.error("parseDate error", e);
        }
        return null;
    }

    /**
     * dateTime
     */
    public static Date parseDate(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        if (source.length() == 10) {
            return parseDate2(source);
        }
        return parseDate(source, yyyyMMddHHmmss);
    }

    /**
     * dateTime
     */
    public static Date parseDateEnglish(String source) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            logger.error("parseDateEnglish error", e);
        }
        return null;
    }

    public static String parseDateEnglish(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            return sdf.format(d);
        } catch (Exception e) {
            logger.error("parseDateEnglish error", e);
        }
        return null;
    }


    /**
     * Date
     */
    public static Date parseDate4(String source) {
        return parseDate(source, yyyyMMddHHmm);
    }

    /**
     * dateTime
     */
    public static Date parseDate3(String source) {

        return parseDate(source, yyyyMMddHHmmss);
    }

    /**
     * Date
     */
    public static Date parseDate2(String source) {
        return parseDate(source, yyMMdd);
    }

    public static Date beforeSeconds(int seconds) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, -seconds);
        return c.getTime();
    }

    public static Date beforeMins(int mins) {
        return beforeSeconds(mins * 60);
    }

    public static Date beforeHours(int hours) {
        return beforeMins(hours * 60);
    }

    /**
     * 是否有效日期
     */
    public static String isBirthday(String date) {
        if (date == null || date.length() != 10) {
            return "";
        }
        DateTime dateTime;
        try {
            dateTime = new DateTime(date);
        } catch (Exception e) {
            return date.substring(0, 4) + "-" + date.substring(5, 7) + "-01";
        }
        return dateTime.toString(yyMMdd);
    }

    /**
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }

        return sb.toString();
    }

    public static String formatTimeMmSs(Long ms) {
        long l = ms / 1000;
        String str = "";
        if (l / 60 > 0) {
            str += (l / 60) + "分钟";
        }
        if (l % 60 > 0) {
            str += (l % 60) + "秒";
        }
        return str;
    }

    // 将毫秒数格式化
    public static String formatTimeSimple(long ms) {
        int hour, minute, second, milli;
        milli = (int) (ms % 1000);
        ms = ms / 1000;
        return formatTimeSecond(ms);
    }

    // 将毫秒数格式化
    public static String formatTimeSecond(long secondTime) {
        int hour, minute, second;
        second = (int) (secondTime % 60);
        secondTime = secondTime / 60;
        minute = (int) (secondTime % 60);
        secondTime = secondTime / 60;
        hour = (int) (secondTime % 60);
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    /**
     * 缓存多少天
     *
     * @param day 天数
     * @return 距离当前秒数
     */
    public static long getCacheDay(int day) {
        return Duration.standardDays(day).getStandardSeconds();
    }

    /**
     * 获取延时后的毫秒数
     *
     * @param sec 延时的时间，单位是秒
     * @return 延时后的毫秒数
     */
    public static long getDelayTimeInMillis(int sec) {
        return now().plusSeconds(sec).getMillis();
    }

    /**
     * 获取延时后的毫秒数
     *
     * @param hours 延时的时间，单位是小时
     * @return 延时后的毫秒数
     */
    public static long getDelayTimeInHours(int hours) {
        return now().plusHours(hours).getMillis();
    }

    /**
     * 获取延时后的毫秒数
     *
     * @param minutes 延时的时间，单位是分钟
     * @return 延时后的毫秒数
     * @author wangyu
     * @since 2017/9/24
     */
    public static long getDelayTimeInMinutes(int minutes) {
        return now().plusMinutes(minutes).getMillis();
    }

    //获取日期差，返回相差秒数。
    //日期格式：2005-01-01
    public static long getCompareDate(String startDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(yyyyMMddHHmmss);
        Date date1 = formatter.parse(startDate);
        Date date2 = new Date();
        long l = date2.getTime() - date1.getTime();
        long s = l;
        return s;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(yyMMdd);
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 计算两个日期之间相差的天数(包括当日)
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static long daysBetween2(Date smdate, Date bdate) {
        return DAYS.between(dateToLocalTime(smdate), dateToLocalTime(bdate)) + 1;
    }

    /**
     * 计算两个日期之间相差的天数(包括当日)
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static long daysBetween2(long smdate, long bdate) {
        return daysBetween2(new Date(smdate), new Date(bdate));
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(yyMMdd);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取当天 0时0分0秒
     *
     * @return
     */
    public static Date getTodayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取当天 23时59分59秒
     *
     * @return
     */
    public static Date getTodayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 字符串的日期格式的计算
     */

    public static List<Date> randomDays(Date from, Date to, int times) {
        int bound = (int) ((to.getTime() - from.getTime()) / 1000);
        Random random = new Random();
        List<Date> dates = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            long t = (long) random.nextInt(bound) * 1000 + from.getTime();
            dates.add(new Date(t));
        }
        return dates;
    }

    /**
     * 随机以前24小时内的时间
     */

    public static Date randomDay() {
        Date start = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        int bound = 60 * 60 * 24;
        Random random = new Random();
        long t = (long) random.nextInt(bound) * 1000 + start.getTime();
        return new Date(t);
    }

    /**
     * 将Date时间转化为UTC时间
     *
     * @param date Date
     * @return String
     * @author mawenjun
     * @Create 2017年3月25日 下午1:53:35
     */
    public static String formatToUTC(Date date, boolean isUGC) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            if (isUGC) {
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            }
            return sdf.format(date);
        } catch (Exception e) {
            logger.error("formatToUTC error", e);
        }
        return null;
    }

    /**
     * 默认使用ugc
     *
     * @param date
     * @return
     */
    public static String formatToUTC(Date date) {
        return formatToUTC(date, true);
    }

    /**
     * 获取显示时间
     *
     * @param createTime
     * @return
     */
    public static String getShowTime(long createTime) {
        long difTime = (System.currentTimeMillis() - createTime) / 1000;
        if (difTime < 60) {//1分钟内
            return "刚刚";
        } else if (difTime < 3600) {//1小时内
            return (difTime / 60) + "分钟前";
        } else if (difTime < 3600 * 24) {//1天内
            return (difTime / 60 / 60) + "小时前";
        } else if (difTime < 3600 * 24 * 7) {//7天内
            return (difTime / 60 / 60 / 24) + "天前";
        }
        return "7天前";
    }

    /**
     * 获取显示时间
     *
     * @param createTime
     * @return
     */
    public static String getShowTimeByIndex(long createTime, int N) {
        long difTime = (System.currentTimeMillis() - createTime) / 1000 / 60;
        if (difTime < N) {// 5分钟内
            return "在线";
        } else {
            return "活跃";
        }
    }

    /**
     * 获取显示时间
     *
     * @param createTime
     * @return
     */
    public static String getShowTime4HeOrShe(long createTime) {
        long difTime = (System.currentTimeMillis() - createTime) / 1000;
        if (difTime < 3600) {//1小时内
            return (difTime / 60) + "分钟前";
        } else if (difTime < 3600 * 24) {//1天内
            return (difTime / 60 / 60) + "小时前";
        }
        return "1天前";
    }

    /**
     * 从当前自然人算起（年月日不包含时分秒），m天之后的 n点
     *
     * @param m m天之后
     * @param n n点
     */
    public static Date getDateTimeAfterMDaysNHoursFromNowDay(int m, int n) {
        Date nowDay = parseDate2(TimeUtil.todayTime());

        Date afterMDay = day3(nowDay, m);

        return new Date(afterMDay.getTime() + n * 60 * 60 * 1000);
    }

    public static String parseCNDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return month + "月" + day + "日" + hour + "点";
    }

    /**
     * 将Timestamp转化成字符串时间
     * 例如：2000-11-11 00:00:00 没有毫秒
     *
     * @param sta
     * @return String
     * @author mawenjun
     * @Create Date 2014-7-18 下午3:19:37
     */
    public static String getTimestampString(Timestamp sta) {
        if (sta == null) {
            return "";
        }
        String ret = "";
        try {
            String str = sta.toString();
            ret = str.substring(0, str.lastIndexOf('.'));
        } catch (Exception e) {
            ret = "";
        }
        return ret;
    }

    /**
     * 将时间戳转换为时间
     *
     * @param time
     * @return
     */
    public static String getTimestampToDate(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为日期
     *
     * @param time
     * @return
     */
    public static String getTimestampToDateStr(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 将Date转换为日期
     *
     * @param date
     * @return
     */
    public static String getDateToDateStr(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间戳转换为时间
     *
     * @param time
     * @return
     */
    public static String getyyyyMMddToDate(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String getYyyyMMddHHmmss(String dateStr) {
        if (dateStr.length() > 19) {
            return dateStr.substring(0, 19);
        } else {
            return dateStr;
        }
    }

    /**
     * 取月日
     *
     * @param time
     * @return
     */
    public static String getToday(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH) + 1 + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
    }

    /**
     * 取月日时分
     *
     * @param time
     * @return 12/12 12:!2
     */
    public static String getTodayHour(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }

    public static Date dateReduceDays(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -num);//当前时间减去num天
        calendar.getTime();
        return calendar.getTime();
    }


    public static String getShowTimeForAuthentication(long time) {
        long difTime = time / 1000;
        if (difTime < 3600) {//1小时内
            return (difTime / 60) + "分钟后";
        } else if (difTime < 3600 * 24) {//1天内
            return (difTime / 60 / 60) + "小时后";
        } else {
            return (difTime / 60 / 60 / 24) + "天后";
        }
    }

    /**
     * 根据时间获取距离明天的秒数
     *
     * @param currentDate
     * @return
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    /**
     * 根据时间获取距离几天后0点的秒数
     *
     * @param currentDate
     * @param days
     * @return
     */
    public static Integer getRemainSecondsDays(Date currentDate, int days) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(days).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    /**
     * 获得离指定时刻最近的00 10 20 30 40 50时刻
     *
     * @param currentDate
     * @return
     */
    public static Date getSpecialTimefromDate(Date currentDate, Integer weight) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
//        boolean isSpecialTime=(minute==0||minute==10||minute==20||minute==30||minute==40||minute==50)&&second==0&&millisecond==0;
        boolean isSpecialTime = (minute % weight == 0) && second == 0 && millisecond == 0;
        if (isSpecialTime) {
            return currentDate;
        } else {
            Calendar specialCalendar = Calendar.getInstance();
            specialCalendar.setTime(currentDate);
            specialCalendar.set(Calendar.MINUTE, 0);
            specialCalendar.set(Calendar.SECOND, 0);
            specialCalendar.set(Calendar.MILLISECOND, 0);
            long minDiff = Long.MAX_VALUE;
            Calendar res = Calendar.getInstance();
            for (int i = 0; i <= 6; i++) {
                long diff = Math.abs(calendar.getTimeInMillis() - specialCalendar.getTimeInMillis());
                if (diff < minDiff && minDiff - diff >= weight * 60000) {
                    minDiff = diff;
                    res.setTime(specialCalendar.getTime());
                } else {
                    break;
                }
                specialCalendar.add(Calendar.MINUTE, weight);
            }
            return res.getTime();
        }
    }

    public static Date getAppointDay(String timeStr) {
        DateFormat dft = new SimpleDateFormat(yyMMdd);
        Calendar cld = Calendar.getInstance();
        Date date = new Date();
        try {
            date = dft.parse(timeStr);
        } catch (ParseException e) {
        }
        return date;
    }


    /**
     * 将时间戳转换为时间 时分秒
     *
     * @param time
     * @return
     */
    public static String getTimestampToDateHour(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 判断指定时间是否在区间内
     *
     * @param nowTime
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getHHCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        String currentHour = simpleDateFormat.format(new Date());
        return currentHour;
    }

    public static String getyyyyMMddCurrentDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDay = simpleDateFormat.format(new Date());
        return currentDay;
    }

    /**
     * 获取本周一 零时零点零分时间戳
     *
     * @return
     */
    public static long getTimesWeekMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTimeInMillis();
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(getTimeByPlusDay(-30));
//        int hour = getHour(new Date());
//        System.out.println(hour);
//        System.out.println(todayEnd());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmm);
//        Date startDate = simpleDateFormat.parse("2019-12-24 01:01");
//        Date entDate = simpleDateFormat.parse("2019-12-25 01:01");
        for (int i = 1; i < 24; i++) {
            Date startDate = simpleDateFormat.parse("2019-12-23 " + (i < 10 ? "0" + i : i) + ":01");
//            for (int j=1;j<24;j++){
//                Date entDate = simpleDateFormat.parse("2019-12-25 "+(j<10?"0"+j:j)+":01");
//            }
        }
//        System.out.println(getDiffBetweenDay(day3(new Date(),-3),new Date()));
//        System.out.println(getAnyMinuteStartTime(new Date()));
//        System.out.println(convertTimeToLong("2019-11-18 00:00:00"));

//        System.out.println( timeCeil(5));
        System.out.println(getFixDay(-3));

    }

    /**
     * 判断是否是当天
     *
     * @param date
     * @return
     */
    public static boolean isNow(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 判断是否是当天
     *
     * @param time
     * @return boolean true|false
     */
    public static boolean isCurrentDay(long time) {
        return isNow(new Date(time));
    }


    /**
     * 判断是否是当天
     *
     * @param dayString
     * @return boolean true|false
     */
    public static boolean isCurrentDay(String dayString) {
        String today = today();
        String day = dayString.substring(0, 10);
        return day.equals(today);
    }

    /**
     * 获取当日为周几，从周日开始返回0-6
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 判断两个时间是不是同一天
     *
     * @param firstDay
     * @param secondDay
     * @return
     */
    public static boolean isEqualDay(Date firstDay, Date secondDay) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //第一个时间
        String first = sf.format(firstDay);
        //第二个时间
        String second = sf.format(secondDay);
        return first.equals(second);
    }


    /**
     * 获取当日小时数
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取 i 天的时间(+i 获取当天之后，-i获取当天之前)
     *
     * @param i
     * @return
     */
    public static long getTimeByPlusDay(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, i);
        return calendar.getTime().getTime();
    }


    /**
     * 获取当日开始时间
     *
     * @return
     */
    public static String todayStart() {
        Date start = getTodayStartTime();
        DateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
        return format.format(start);
    }

    /**
     * 获取当日结束时间
     *
     * @return
     */
    public static String todayEnd() {
        Date start = getTodayEndTime();
        DateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
        return format.format(start);
    }

    public static Date getAnyDay(String dateStr) {
        try {
            return new SimpleDateFormat(yyMMdd).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获得今天零点时间戳
     *
     * @return
     */
    public static Long getTodayZeroPointTimestamps() {
        Long currentTimestamps = System.currentTimeMillis();
        Long oneDayTimestamps = Long.valueOf(60 * 60 * 24 * 1000);
        return currentTimestamps - (currentTimestamps + 60 * 60 * 8 * 1000) % oneDayTimestamps;
    }

    public static Date getAnyDayStartTime(Date date) throws Exception {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return formater2.parse(formater.format(date) + " 00:00:00");
    }

    public static Date getAnyDayEndTime(Date date) throws Exception {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return formater2.parse(formater.format(date) + " 23:59:59");
    }

    public static Date getAnyHourStartTime(Date date) throws Exception {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:");
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return formater2.parse(formater.format(date) + "00:00");
    }

    public static Date getAnyHourEndTime(Date date) throws Exception {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:");
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return formater2.parse(formater.format(date) + "59:59");
    }

    public static Date getAnyMinuteStartTime(Date date) throws Exception {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm:");
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return formater2.parse(formater.format(date) + "00");
    }

    public static Date getAnyMinuteEndTime(Date date) throws Exception {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm:");
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return formater2.parse(formater.format(date) + "59");
    }

    /**
     * 字符串转时间戳
     *
     * @param time
     * @return
     */
    public static Long convertTimeToLong(String time) {
        Assert.notNull(time, "time is null");
        DateTimeFormatter format = DateTimeFormatter.ofPattern(yyyyMMddHHmmss);
        LocalDateTime parse = LocalDateTime.parse(time, format);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongTime(String nowTime, String beginTime, String endTime) {
        boolean ok = false;
        try {
            String baseTime = LocalDate.now().toString();
            Long nowStamp = convertTimeToLong(String.format("%s %s", baseTime, nowTime));
            Long beginStamp = convertTimeToLong(String.format("%s %s", baseTime, beginTime));
            Long endStamp = convertTimeToLong(String.format("%s %s", baseTime, endTime));
            if (beginStamp <= nowStamp && nowStamp <= endStamp) {
                ok = true;
            }
        } catch (Exception e) {
            logger.error("belongTime error", e);
        }
        return ok;
    }

    /**
     * @return
     * @des 获取前days天的00:00:01的毫秒数
     */
    public static Long getStartTime(int days) {
        LocalDate day7 = LocalDate.now().minusDays(days);
        LocalDateTime time = LocalDateTime.of(day7, LocalTime.MIN);
        return time.toInstant(ZoneOffset.of("+8")).toEpochMilli() + 1000;
    }

    /**
     * @return
     * @des 获取当天23:23:59 的毫秒数
     */
    public static Long getEndTime() {
        LocalDate day7 = LocalDate.now().minusDays(1);
        LocalDateTime time = LocalDateTime.of(day7, LocalTime.MAX);
        return time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }


    /**
     * 日期加n天
     *
     * @param s
     * @param n
     * @return
     */
    public static long addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);//增加一天
            //cd.add(Calendar.MONTH, n);//增加一个月

            return cd.getTimeInMillis();

        } catch (Exception e) {
            return -1;
        }
    }


    /**
     * 获取本年第几周
     *
     * @return
     */
    public static int getWeekOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;

    }

    /**
     * 获取本周一00:00的时间
     *
     * @return
     */
    public static Date getThisWeekMonday() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获取指定时间戳所在那周指定星期X的时间戳
     *
     * @param time          时间戳
     * @param needDayOfWeek 星期X
     * @return 时间戳
     */
    public static long getWeekday(long time, int needDayOfWeek) {
        if (time == 0L) {
            time = System.currentTimeMillis();
        }
        DateTime dateTime = new DateTime(time);
        int dayOfWeek = dateTime.getDayOfWeek();
        DateTime firstDay = dateTime;
        if (dayOfWeek > 0) {
            firstDay = dateTime.minusDays(dayOfWeek);
        }
        return firstDay.plusDays(needDayOfWeek).toLocalDate().toDate().getTime();
    }
}
