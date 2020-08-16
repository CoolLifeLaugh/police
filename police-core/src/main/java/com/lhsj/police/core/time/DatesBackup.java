package com.lhsj.police.core.time;//package com.lhsj.police.core.util;
//
//import com.lhsj.police.core.log.ReLogs;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * date util，from springside
// */
//public class ReDates {
//
//    /**
//     * date + time + millis
//     */
//    private static final String DATETIME_MILLIS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
//
//    /**
//     * date + time
//     */
//    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
//
//    /**
//     * date
//     */
//    private static final String DATE_FORMAT = "yyyy-MM-dd";
//
//    /**
//     * time
//     */
//    private static final String TIME_FORMAT = "HH:mm:ss";
//
//    // 注意SimpleDateFormat不是线程安全的
//
//    /**
//     *
//     */
//    private static ThreadLocal<SimpleDateFormat> ThreadDateTimeMillis = new ThreadLocal<SimpleDateFormat>();
//    private static ThreadLocal<SimpleDateFormat> ThreadDateTime = new ThreadLocal<SimpleDateFormat>();
//    private static ThreadLocal<SimpleDateFormat> ThreadDate = new ThreadLocal<SimpleDateFormat>();
//    private static ThreadLocal<SimpleDateFormat> ThreadTime = new ThreadLocal<SimpleDateFormat>();
//
//    private static SimpleDateFormat dateTimeMillisInstance() {
//        SimpleDateFormat df = ThreadDateTimeMillis.get();
//        if (df == null) {
//            df = new SimpleDateFormat(DATETIME_MILLIS_FORMAT);
//            ThreadDateTimeMillis.set(df);
//        }
//        return df;
//    }
//
//    private static SimpleDateFormat dateTimeInstance() {
//        SimpleDateFormat df = ThreadDateTime.get();
//        if (df == null) {
//            df = new SimpleDateFormat(DATETIME_FORMAT);
//            ThreadDateTime.set(df);
//        }
//        return df;
//    }
//
//    private static SimpleDateFormat dateInstance() {
//        SimpleDateFormat df = ThreadDate.get();
//        if (df == null) {
//            df = new SimpleDateFormat(DATE_FORMAT);
//            ThreadDate.set(df);
//        }
//        return df;
//    }
//
//    private static SimpleDateFormat timeInstance() {
//        SimpleDateFormat df = ThreadTime.get();
//        if (df == null) {
//            df = new SimpleDateFormat(TIME_FORMAT);
//            ThreadTime.set(df);
//        }
//        return df;
//    }
//
//    /**
//     * 获取当前日期时间，带毫秒
//     *
//     * @return 返回当前时间的字符串值
//     */
//    public static String dateTimeMillis() {
//        return dateTimeMillisInstance().format(new Date());
//    }
//
//    /**
//     * 将指定的时间格式化成出返回，带毫秒
//     *
//     * @param date
//     * @return
//     */
//    public static String dateTimeMillis(Date date) {
//        return dateTimeMillisInstance().format(date);
//    }
//
//    /**
//     * 将指定的字符串解析为时间类型，带毫秒
//     *
//     * @param datestr
//     * @return
//     * @throws ParseException
//     */
//    public static Date dateTimeMillis(String datestr) throws ParseException {
//        return dateTimeMillisInstance().parse(datestr);
//    }
//
//    /**
//     * 获取当前日期时间
//     *
//     * @return 返回当前时间的字符串值
//     */
//    public static String dateTime() {
//        return dateTimeInstance().format(new Date());
//    }
//
//    /**
//     * 将指定的时间格式化成出返回
//     *
//     * @param date
//     * @return
//     */
//    public static String dateTime(Date date) {
//        return dateTimeInstance().format(date);
//    }
//
//    /**
//     * 将指定的字符串解析为时间类型
//     *
//     * @param datestr
//     * @return
//     * @throws ParseException
//     */
//    public static Date dateTime(String datestr) throws ParseException {
//        return dateTimeInstance().parse(datestr);
//    }
//
//    /**
//     * 获取当前的日期
//     *
//     * @return
//     */
//    public static String date() {
//        return dateInstance().format(new Date());
//    }
//
//    /**
//     * 将指定的时间格式化成出返回
//     *
//     * @param date
//     * @return
//     */
//    public static String date(Date date) {
//        return dateInstance().format(date);
//    }
//
//    /**
//     * 将指定的字符串解析为时间类型
//     *
//     * @param dateStr
//     * @return
//     * @throws ParseException
//     */
//    public static Date date(String dateStr) throws ParseException {
//        return dateInstance().parse(dateStr);
//    }
//
//    /**
//     * 获取当前的时间
//     *
//     * @return
//     */
//    public static String time() {
//        return timeInstance().format(new Date());
//    }
//
//    /**
//     * 讲指定的时间格式化成出返回
//     *
//     * @param date
//     * @return
//     */
//    public static String time(Date date) {
//        return timeInstance().format(date);
//    }
//
//    /**
//     * 将指定的字符串解析为时间类型
//     *
//     * @param dateStr
//     * @return
//     * @throws ParseException
//     */
//    public static Date time(String dateStr) throws ParseException {
//        return timeInstance().parse(dateStr);
//    }
//
//    /**
//     * 在当前时间的基础上加或减去year年
//     *
//     * @param year
//     * @return
//     */
//    public static Date year(int year) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.YEAR, year);
//        return cal.getTime();
//    }
//
//    /**
//     * 在指定的时间上加或减去几年
//     *
//     * @param date
//     * @param year
//     * @return
//     */
//    public static Date year(Date date, int year) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.YEAR, year);
//        return cal.getTime();
//    }
//
//    /**
//     * 在当前时间的基础上加或减去几月
//     *
//     * @param month
//     * @return
//     */
//    public static Date month(int month) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.MONTH, month);
//        return cal.getTime();
//    }
//
//    /**
//     * 在指定的时间上加或减去几月
//     *
//     * @param date
//     * @param month
//     * @return
//     */
//    public static Date month(Date date, int month) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.MONTH, month);
//        return cal.getTime();
//    }
//
//    /**
//     * 在当前时间的基础上加或减去几天
//     *
//     * @param day
//     * @return
//     */
//    public static Date day(int day) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.DAY_OF_YEAR, day);
//        return cal.getTime();
//    }
//
//    /**
//     * 在指定的时间上加或减去几天
//     *
//     * @param date
//     * @param day
//     * @return
//     */
//    public static Date day(Date date, int day) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.DAY_OF_YEAR, day);
//        return cal.getTime();
//    }
//
//    /**
//     * 在当前时间的基础上加或减去几小时-支持浮点数
//     *
//     * @param hour
//     * @return
//     */
//    public static Date hour(float hour) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.MINUTE, (int)(hour * 60));
//        return cal.getTime();
//    }
//
//    /**
//     * 在制定的时间上加或减去几小时-支持浮点数
//     *
//     * @param date
//     * @param hour
//     * @return
//     */
//    public static Date hour(Date date, float hour) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.MINUTE, (int)(hour * 60));
//        return cal.getTime();
//    }
//
//    /**
//     * 在当前时间的基础上加或减去几分钟
//     *
//     * @param minute
//     * @return
//     */
//    public static Date minute(int minute) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.MINUTE, minute);
//        return cal.getTime();
//    }
//
//    /**
//     * 在制定的时间上加或减去几分钟
//     *
//     * @param date
//     * @param minute
//     * @return
//     */
//    public static Date minute(Date date, int minute) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.MINUTE, minute);
//        return cal.getTime();
//    }
//
//    /**
//     * 在当前时间的基础上加或减去几秒钟
//     *
//     * @param second
//     * @return
//     */
//    public static Date second(int second) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.SECOND, second);
//        return cal.getTime();
//    }
//
//    /**
//     * 在制定的时间上加或减去几秒钟
//     *
//     * @param date
//     * @param second
//     * @return
//     */
//    public static Date second(Date date, int second) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.SECOND, second);
//        return cal.getTime();
//    }
//
//    /**
//     * 判断字符串是否为日期字符串
//     *
//     * @param date 日期字符串
//     * @return true or false
//     */
//    public static boolean isDate(String date) {
//        try {
//            dateTimeInstance().parse(date);
//            return true;
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//        }
//        return false;
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位秒
//     *
//     * @param date1
//     * @param date2
//     * @return 秒
//     */
//    public static long subtract(Date date1, Date date2) {
//        long cha = (date2.getTime() - date1.getTime()) / 1000;
//        return cha;
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位秒
//     *
//     * @param date1
//     * @param date2
//     * @return 秒
//     */
//    public static long subtract(String date1, String date2) {
//        long rs = 0;
//        try {
//            Date start = dateTimeInstance().parse(date1);
//            Date end = dateTimeInstance().parse(date2);
//            long cha = (end.getTime() - start.getTime()) / 1000;
//            rs = cha;
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//        }
//        return rs;
//    }
//
//    /**
//     * 时间date1和date2的时间差 -单位分钟
//     *
//     * @param date1
//     * @param date2
//     * @return 分钟
//     */
//    public static int subtractMinute(String date1, String date2) {
//        int rs = 0;
//        try {
//            Date start = dateTimeInstance().parse(date1);
//            Date end = dateTimeInstance().parse(date2);
//            long cha = (end.getTime() - start.getTime()) / 1000;
//            rs = (int)cha / (60);
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//        }
//        return rs;
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位分钟
//     *
//     * @param date1
//     * @param date2
//     * @return 分钟
//     */
//    public static int subtractMinute(Date date1, Date date2) {
//        long cha = date2.getTime() - date1.getTime();
//        return (int)cha / (1000 * 60);
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位小时
//     *
//     * @param date1
//     * @param date2
//     * @return 小时
//     */
//    public static int subtractHour(Date date1, Date date2) {
//        long cha = (date2.getTime() - date1.getTime()) / 1000;
//        return (int)cha / (60 * 60);
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位小时
//     *
//     * @param date1
//     * @param date2
//     * @return 小时
//     */
//    public static int subtractHour(String date1, String date2) {
//        int rs = 0;
//        try {
//            Date start = dateTimeInstance().parse(date1);
//            Date end = dateTimeInstance().parse(date2);
//            long cha = (end.getTime() - start.getTime()) / 1000;
//            rs = (int)cha / (60 * 60);
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//        }
//        return rs;
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位天
//     *
//     * @param date1
//     * @param date2
//     * @return 天
//     */
//    public static int subtractDay(String date1, String date2) {
//        int rs = 0;
//        try {
//            Date start = dateTimeInstance().parse(date1);
//            Date end = dateTimeInstance().parse(date2);
//            long sss = (end.getTime() - start.getTime()) / 1000;
//            rs = (int)sss / (60 * 60 * 24);
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//        }
//        return rs;
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位天
//     *
//     * @param date1
//     * @param date2
//     * @return 天
//     */
//    public static int subtractDay(Date date1, Date date2) {
//        long cha = date2.getTime() - date1.getTime();
//        return (int)cha / (1000 * 60 * 60 * 24);
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位月
//     *
//     * @param date1
//     * @param date2
//     * @return 月
//     */
//    public static int subtractMonth(String date1, String date2) {
//        int result;
//        Calendar c1 = Calendar.getInstance();
//        Calendar c2 = Calendar.getInstance();
//        try {
//            c1.setTime(dateInstance().parse(date1));
//            c2.setTime(dateInstance().parse(date2));
//            int year1 = c1.get(Calendar.YEAR);
//            int month1 = c1.get(Calendar.MONTH);
//            int year2 = c2.get(Calendar.YEAR);
//            int month2 = c2.get(Calendar.MONTH);
//            if (year1 == year2) {
//                result = month2 - month1;
//            } else {
//                result = 12 * (year2 - year1) + month2 - month1;
//            }
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//            result = -1;
//        }
//        return result;
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位月
//     *
//     * @param date1
//     * @param date2
//     * @return 月
//     */
//    public static int subtractMonth(Date date1, Date date2) {
//        int result;
//        Calendar c1 = Calendar.getInstance();
//        Calendar c2 = Calendar.getInstance();
//        c1.setTime(date1);
//        c2.setTime(date2);
//        int year1 = c1.get(Calendar.YEAR);
//        int month1 = c1.get(Calendar.MONTH);
//        int year2 = c2.get(Calendar.YEAR);
//        int month2 = c2.get(Calendar.MONTH);
//        if (year1 == year2) {
//            result = month2 - month1;
//        } else {
//            result = 12 * (year2 - year1) + month2 - month1;
//        }
//        return result;
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位年
//     *
//     * @param date1
//     * @param date2
//     * @return 年
//     */
//    public static int subtractYear(String date1, String date2) {
//        int result;
//        Calendar c1 = Calendar.getInstance();
//        Calendar c2 = Calendar.getInstance();
//        try {
//            c1.setTime(dateInstance().parse(date1));
//            c2.setTime(dateInstance().parse(date2));
//            int year1 = c1.get(Calendar.YEAR);
//            int year2 = c2.get(Calendar.YEAR);
//            result = year2 - year1;
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//            result = -1;
//        }
//        return result;
//    }
//
//    /**
//     * 时间date1和date2的时间差-单位年
//     *
//     * @param date1
//     * @param date2
//     * @return 年
//     */
//    public static int subtractYear(Date date1, Date date2) {
//        int result;
//        Calendar c1 = Calendar.getInstance();
//        Calendar c2 = Calendar.getInstance();
//        c1.setTime(date1);
//        c2.setTime(date2);
//        int year1 = c1.get(Calendar.YEAR);
//        int year2 = c2.get(Calendar.YEAR);
//        result = year2 - year1;
//        return result;
//    }
//
//    /**
//     * 获取俩个时间的查结果用时秒表示
//     *
//     * @param date1
//     * @param date2
//     * @return 几小时:几分钟:几秒钟
//     * @Summary:此处可以讲计算结果包装成一个结构体返回便于格式化
//     */
//    public static String subtractTime(String date1, String date2) {
//        String result = "";
//        try {
//            Date start = dateTimeInstance().parse(date1);
//            Date end = dateTimeInstance().parse(date2);
//            long sss = (end.getTime() - start.getTime()) / 1000;
//            int hh = (int)sss / (60 * 60);
//            int mm = (int)(sss - hh * 60 * 60) / (60);
//            int ss = (int)(sss - hh * 60 * 60 - mm * 60);
//            result = hh + ":" + mm + ":" + ss;
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//        }
//        return result;
//    }
//
//    /**
//     * 获取俩个时间的查结果用时秒表示
//     *
//     * @param date1
//     * @param date2
//     * @return 几天-几小时:几分钟:几秒钟
//     * @Summary:此处可以讲计算结果包装成一个结构体返回便于格式化
//     */
//    public static String subtractDate(String date1, String date2) {
//        String result = "";
//        try {
//            Date start = dateTimeInstance().parse(date1);
//            Date end = dateTimeInstance().parse(date2);
//            long sss = (end.getTime() - start.getTime()) / 1000;
//            int dd = (int)sss / (60 * 60 * 24);
//            int hh = (int)(sss - dd * 60 * 60 * 24) / (60 * 60);
//            int mm = (int)(sss - dd * 60 * 60 * 24 - hh * 60 * 60) / (60);
//            int ss = (int)(sss - dd * 60 * 60 * 24 - hh * 60 * 60 - mm * 60);
//            result = dd + "-" + hh + ":" + mm + ":" + ss;
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//        }
//        return result;
//    }
//
//    /**
//     * 获取俩个时间之前的相隔的天数
//     *
//     * @param startTime
//     * @param endTime
//     * @return
//     * @throws ParseException
//     */
//    public static int subDay(Date startTime, Date endTime) {
//        int days = 0;
//        Calendar can1 = Calendar.getInstance();
//        can1.setTime(startTime);
//        Calendar can2 = Calendar.getInstance();
//        can2.setTime(endTime);
//        int year1 = can1.get(Calendar.YEAR);
//        int year2 = can2.get(Calendar.YEAR);
//
//        Calendar can = null;
//        if (can1.before(can2)) {
//            days -= can1.get(Calendar.DAY_OF_YEAR);
//            days += can2.get(Calendar.DAY_OF_YEAR);
//            can = can1;
//        } else {
//            days -= can2.get(Calendar.DAY_OF_YEAR);
//            days += can1.get(Calendar.DAY_OF_YEAR);
//            can = can2;
//        }
//        for (int i = 0; i < Math.abs(year2 - year1); i++) {
//            days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
//            can.add(Calendar.YEAR, 1);
//        }
//
//        return days;
//    }
//
//    /**
//     * 获取俩个时间之前的相隔的天数
//     *
//     * @param startTime
//     * @param endTime
//     * @return
//     * @throws ParseException
//     */
//    public static int subDay(String startTime, String endTime) {
//        int days = 0;
//        try {
//            Date date1 = dateInstance().parse(dateInstance().format(dateTimeInstance().parse(startTime)));
//            Date date2 = dateInstance().parse(dateInstance().format(dateTimeInstance().parse(endTime)));
//            Calendar can1 = Calendar.getInstance();
//            can1.setTime(date1);
//            Calendar can2 = Calendar.getInstance();
//            can2.setTime(date2);
//            int year1 = can1.get(Calendar.YEAR);
//            int year2 = can2.get(Calendar.YEAR);
//
//            Calendar can = null;
//            if (can1.before(can2)) {
//                days -= can1.get(Calendar.DAY_OF_YEAR);
//                days += can2.get(Calendar.DAY_OF_YEAR);
//                can = can1;
//            } else {
//                days -= can2.get(Calendar.DAY_OF_YEAR);
//                days += can1.get(Calendar.DAY_OF_YEAR);
//                can = can2;
//            }
//            for (int i = 0; i < Math.abs(year2 - year1); i++) {
//                days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
//                can.add(Calendar.YEAR, 1);
//            }
//
//        } catch (ParseException e) {
//            ReLogs.error(e.getMessage(), e);
//        }
//        return days;
//    }
//
//    /**
//     * get first date of given month and year
//     *
//     * @param year
//     * @param month
//     * @return
//     */
//    public static String getFirstDayOfMonth(int year, int month) {
//        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
//        return year + "-" + monthStr + "-" + "01";
//    }
//
//    /**
//     * get the last date of given month and year
//     *
//     * @param year
//     * @param month
//     * @return
//     */
//    public static String getLastDayOfMonth(int year, int month) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month - 1);
//        calendar.set(Calendar.DATE, 1);
//        calendar.add(Calendar.MONTH, 1);
//        calendar.add(Calendar.DAY_OF_YEAR, -1);
//        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
//    }
//
//    /**
//     * get Calendar of given year
//     *
//     * @param year
//     * @return
//     */
//    private static Calendar getCalendarFormYear(int year) {
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        cal.set(Calendar.YEAR, year);
//        return cal;
//    }
//
//    /**
//     * get start date of given week no of a year
//     *
//     * @param year
//     * @param weekNo
//     * @return
//     */
//    public static String getStartDayOfWeekNo(int year, int weekNo) {
//        Calendar cal = getCalendarFormYear(year);
//        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
//        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
//
//    }
//
//    /**
//     * get the end day of given week no of a year.
//     *
//     * @param year
//     * @param weekNo
//     * @return
//     */
//    public static String getEndDayOfWeekNo(int year, int weekNo) {
//        Calendar cal = getCalendarFormYear(year);
//        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
//        cal.add(Calendar.DAY_OF_WEEK, 6);
//        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
//    }
//
//}
