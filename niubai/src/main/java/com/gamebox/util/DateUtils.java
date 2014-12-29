package com.gamebox.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateUtils {

    public final static String MONTH_FORMAT = "yyyy-MM";

    public final static String DAY_FORMAT = "yyyy-MM-dd";

    public final static String TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    private DateUtils() {

    }

    /**
     * 时间戳转日期
     * 
     * @param date
     *            时间戳
     * @return 日期Date类型
     */
    public static Date timestampToDate(long date) {

        if (date == 0L)
            return null;
        else
            return new Date(date * 1000L);
    }

    /**
     * 时间戳转日期
     * 
     * @param date
     *            时间戳
     * @return 日期String类型
     */
    public static String convertTimeStampToDateToString(long time) {

        Date date = timestampToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
        return sdf.format(date);
    }

    /**
     * 时间戳转日期
     * 
     * @param date
     *            时间戳
     * @return 日期String类型
     */
    public static String convertTimeStampToDateToStringS(long time) {

        Date date = timestampToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 获取指定日期在指定天数后日期
     * 
     * @param date
     *            日期
     * @param days
     *            天数
     * @return 日期
     */
    public static Date addDay(Date date, int days) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    /**
     * 获取当前日期
     * 
     * @param format
     *            格式
     * @return 日期
     */
    public static String getCurrentDay(String format) {

        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(date);
    }

    /**
     * String转Date
     */
    public static Date convertStringToDate(String dateTime, String format) {

        try {
            SimpleDateFormat dateformat = new SimpleDateFormat(format);
            return dateformat.parse(dateTime);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param date
     * @param pattern
     *            可选类型 1:HH:mm,2:yyyy-MM-dd,3:yyyy-MM-dd HH:mm,4:yyyy-MM-dd HH:mm:ss,
     *            5:yyyyMMdd,6:yyyyMMddHHmmss,7:yyyyMMddHHmmssms,8:MM-dd
     * @return
     */
    public static String convertDateToString(Date date, String pattern, String timeZone) {

        if (date == null || pattern == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if (timeZone != null)
            format.setTimeZone(TimeZone.getTimeZone("GMT" + timeZone + ":00"));
        return format.format(date);
    }

    /**
     * @param oldTime
     *            较小的时间
     * @param newTime
     *            较大的时间 (如果为空 默认当前时间 ,表示和当前时间相比)
     * @return -1 ：同一天. 0：昨天 . 1 ：至少是前天.
     * @throws ParseException
     *             转换异常
     */
    public int isToday(Date oldTime, Date newTime) throws ParseException {

        if (newTime == null) {
            newTime = new Date();
        }
        // yyyy-MM-dd 00：00：00
        SimpleDateFormat format = new SimpleDateFormat(DAY_FORMAT);
        String todayStr = format.format(newTime);
        Date today = format.parse(todayStr);
        // 昨天 86400000=24*60*60*1000 一天
        if ((today.getTime() - oldTime.getTime()) > 0 && (today.getTime() - oldTime.getTime()) <= 86400000) {
            return 0;
        }
        else if ((today.getTime() - oldTime.getTime()) <= 0) { // 至少是今天
            return -1;
        }
        else { // 至少是前天
            return 1;
        }
    }

    /**
     * 得到几小时前或后的时间 hours为负则为之前
     * 
     * @param date
     * @param hours
     * @return
     */
    public static String getHourBeforeOrAfter(Date date, int hours) {

        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.roll(Calendar.HOUR_OF_DAY, hours);
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(cld.getTime());
    }

    /**
     * 得到几小时前或后的时间 hours为负则为之前
     * 
     * @param date
     * @param hours
     * @return
     */
    public static Date getDateOfHourBeforeOrAfter(Date date, int hours) {

        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.HOUR, cld.get(Calendar.HOUR) + hours);
//        cld.roll(Calendar.HOUR_OF_DAY, hours);
        return cld.getTime();
    }

    /**
     * 得到几天前的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date date, int days) {

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - days);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date date, int days) {

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
        return now.getTime();
    }

    /**
     * 获取当前服务器日期 格式2020/01/01
     * 
     * @return
     */
    public static String getCurrentDate(String format) throws IllegalArgumentException {

        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(date);
    }

    /**
     * 获取当月的 第一天
     * 
     * @return
     */
    public static String getFirstDayOfMonth() {

        Calendar cal = Calendar.getInstance();
        Calendar f = (Calendar) cal.clone();
        f.clear();
        f.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        f.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        String firstday = new SimpleDateFormat(DAY_FORMAT).format(f.getTime());
        return firstday;
    }

    /**
     * 获取当月
     * 
     * @return
     */
    public static String getCurrentMonth() {

        Calendar cal = Calendar.getInstance();
        Calendar f = (Calendar) cal.clone();
        f.clear();
        f.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        f.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        String month = new SimpleDateFormat(MONTH_FORMAT).format(f.getTime());
        return month;
    }

    /**
     * 获得 指定日期的前一个月的 第一天和最后一天
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Map<String, String> getLastMonth(String date) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat(DAY_FORMAT);

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(date));

        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first_prevM = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first_prevM);
        day_first_prevM = str.toString();

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        String day_end_prevM = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_end_prevM);
        day_end_prevM = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("lastMonthFirstDay", day_first_prevM);
        map.put("lastMonthEndDay", day_end_prevM);
        return map;
    }

    // 得到两个时间相差几天 参数形式 yyyy-mm-dd
    public static long daysBetween(String fromDay, String toDay, String format) throws Exception {

        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        Date fromDate = dateformat.parse(fromDay);
        Date toDate = dateformat.parse(toDay);
        return (toDate.getTime() - fromDate.getTime() + 1000000) / (3600 * 24 * 1000);
    }

    /**
     * 获取当前时间 不要怀疑，就是要用int类型，我们是高大上的全球平台，为了满足不同时区的国际化。 哈哈，膜拜吧！颤抖吧！
     * 
     * @return 获取当前时间
     */
    public static Integer getCurrentTime() {

        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 获取当前时间 不要怀疑，就是要用int类型，我们是高大上的全球平台，为了满足不同时区的国际化。 哈哈，膜拜吧！颤抖吧！
     * 
     * @return 获取当前时间
     */
    public static Integer timeToTimestamp(String time) {

        int timestamp = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
            Date d = sdf.parse(time);
            timestamp = (int) (d.getTime() / 1000);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

}
