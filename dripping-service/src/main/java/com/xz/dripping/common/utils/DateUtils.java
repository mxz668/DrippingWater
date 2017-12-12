package com.xz.dripping.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能: 日期工具类
 */
public class DateUtils {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public final static String DEFAULT_DATA_FORMAT = "yyyy-MM-dd";

    public final static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SS";

    public final static String COMMON_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

    public final static String ONLY_DATA_FORMAT = "yyyyMMdd";



    /**
     * 年月格式化
     */
    public static final String DATE_FORMAT_YYMM = "yyMM";

    /**
     * 年月日格式化
     */
    public static final String DATE_FORMAT_YYMMDD = "yyyyMMdd";
    /**
     * 年月日时分秒格式化
     */
    public static final String DATE_FORMAT_YYMMDDHHMMSS = "yyyyMMddhhmmss";

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 转换日期
     *
     * @param str
     * @param format
     * @return
     */
    public static Date parse(String str, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(str);
        } catch (Exception e) {
            logger.error(String.format("将日期%s按格式%s转换为日期失败", str, format), e);
        }
        return null;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            logger.error(String.format("将日期按格式%s格式化失败", format), e);
        }
        return null;
    }
    /**
     * 格式化日期
     *
     * @param date
     * @param :yyyyMMddhhmmss
     * @return
     */
    public static String formatDateNotNull(Date date,String format) {
        try {
            if(date==null){
                return "";
            }
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            logger.error(String.format("将日期按格式%s格式化失败", format), e);
        }
        return null;
    }
    /**
     * 比较日期
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDay(Date date1, Date date2) {
        date1 = parse(format(date1, "yyyyMMdd"), "yyyyMMdd");
        date2 = parse(format(date2, "yyyyMMdd"), "yyyyMMdd");
        return compare(date1, date2);
    }

    /**
     * 比较日期
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compare(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return 1;
        } else if (date1.getTime() < date2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    public static int daysBetween(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date2);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /**
     * 取得两个时间段的时间间隔
     * return t2 与t1的间隔天数
     * throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getBetweenDays(String t1, String t2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays = 0;
        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            c1 = c2;
            c2.setTime(d1);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }

    /**
     * 取得两个时间段的时间间隔
     * return t2 与t1的间隔天数
     * throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getBetweenDays(Date d1, Date d2) throws ParseException {
        int betweenDays = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            c1 = c2;
            c2.setTime(d1);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }

    public static int dateCompare(Date dt1, Date dt2) {
        try {
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static boolean isAfter(Date dt1, Date dt2) {
        if (dateCompare(dt1, dt2) == 1) {
            return true;
        }
        return false;
    }

    public static boolean isAfterOrEqual(Date dt1, Date dt2) {
        if (dateCompare(dt1, dt2) == 1 || dateCompare(dt1, dt2) == 0) {
            return true;
        }
        return false;
    }

    public static boolean isBeforeOrEqual(Date dt1, Date dt2) {
        if (dateCompare(dt1, dt2) == -1 || dateCompare(dt1, dt2) == 0) {
            return true;
        }
        return false;
    }

    public static boolean isBefore(Date dt1, Date dt2) {
        if (dateCompare(dt1, dt2) == -1) {
            return true;
        }
        return false;
    }





}
