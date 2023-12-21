package com.xiaoyai.common.activiti.core.util;


import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DateUtil {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

    public static final String LAST_TIME = "2038-01-01 00:00:00";

    private static Map<String, SimpleDateFormat> formats = new HashMap();
    public static final String DATE_FORMATE_STRING_DEFAULT = "yyyyMMddHHmmss";
    public static final String DATE_FORMATE_STRING_A = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMATE_STRING_B = "yyyy-MM-dd";
    public static final String DATE_FORMATE_STRING_C = "MM/dd/yyyy HH:mm:ss a";
    public static final String DATE_FORMATE_STRING_D = "yyyy-MM-dd HH:mm:ss a";
    public static final String DATE_FORMATE_STRING_E = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMATE_STRING_F = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_FORMATE_STRING_G = "yyyy-MM-dd'T'HH:mm:ssz";
    public static final String DATE_FORMATE_STRING_H = "yyyyMMdd";
    public static final String DATE_FORMATE_STRING_I = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMATE_STRING_J = "yyyyMMddHHmmss.SSS";
    public static final String DATE_FORMATE_STRING_K = "yyyyMMddHHmmssSSS";
    public static final String DATE_FORMATE_STRING_L= "yyyy-MM";
    public static final String DATE_FORMATE_STRING_M= "yyyy-M";
    public static final String DATE_FORMATE_STRING_N= "MM-dd";

    static {
        formats.put("yyyyMMddHHmmss", new SimpleDateFormat("yyyyMMddHHmmss"));
        formats.put("yyyy-MM-dd HH:mm:ss", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        formats.put("yyyy-MM-dd", new SimpleDateFormat("yyyy-MM-dd"));
        formats.put("MM/dd/yyyy HH:mm:ss a", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a"));
        formats.put("yyyy-MM-dd HH:mm:ss a", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a"));
        formats.put("yyyy-MM-dd'T'HH:mm:ss'Z'", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        formats.put("yyyy-MM-dd'T'HH:mm:ssZ", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
        formats.put("yyyy-MM-dd'T'HH:mm:ssz", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz"));
        formats.put("yyyyMMdd", new SimpleDateFormat("yyyyMMdd"));
        formats.put("yyyy-MM-dd HH:mm:ss.SSS", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        formats.put("yyyyMMddHHmmss.SSS", new SimpleDateFormat("yyyyMMddHHmmss.SSS"));
        formats.put("yyyyMMddHHmmssSSS", new SimpleDateFormat("yyyyMMddHHmmssSSS"));
    }

    /**
     * 返回 yyyyMMddhhmmss 格式的日期串
     *
     * @return
     */
    public static String getyyyyMMddhhmmssDateString() {
        return dateFormat.format(new Date());
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日
     *
     * @return
     */
    public static int getCurrentDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前周
     *
     * @return
     */
    public static String getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String result = "";
        switch (dayOfWeek) {
            case 1:
                result = "星期一";
                break;
            case 2:
                result = "星期二";
                break;
            case 3:
                result = "星期三";
                break;
            case 4:
                result = "星期四";
                break;
            case 5:
                result = "星期五";
                break;
            case 6:
                result = "星期六";
                break;
            case 7:
                result = "星期日";
                break;
            default:
                result = "周一";
                break;
        }
        return result;
    }

    /**
     * 获取当前周
     *
     * @return
     */
    public static String getDayOfWeek(Date _date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(_date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String result = "";
        switch (dayOfWeek) {
            case 1:
                result = "星期一";
                break;
            case 2:
                result = "星期二";
                break;
            case 3:
                result = "星期三";
                break;
            case 4:
                result = "星期四";
                break;
            case 5:
                result = "星期五";
                break;
            case 6:
                result = "星期六";
                break;
            case 7:
                result = "星期日";
                break;
            default:
                result = "周一";
                break;
        }
        return result;
    }

    public static Date getLastDate() throws ParseException {
        return getDateFromString("3000-01-01", DATE_FORMATE_STRING_B);
    }

    /**
     * 转TimeStamp
     *
     * @param date
     * @return
     */
    public static Timestamp getTimestamp(Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    /**
     * 获取未来时间
     *
     * @param second 秒
     * @return
     */
    public static Date getFutureDate(int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static String getFormatTimeString(Date date, String pattern) {
        SimpleDateFormat sDateFormat = getDateFormat(pattern);

        synchronized (sDateFormat) {
            return sDateFormat.format(date);
        }
    }

    public static String getDefaultFormateTimeString(Date date) {
        return getFormatTimeString(date, "yyyyMMddHHmmss");
    }

    public static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat sDateFormat = (SimpleDateFormat) formats.get(pattern);
        if (sDateFormat == null) {
            sDateFormat = new SimpleDateFormat(pattern);
            formats.put(pattern, sDateFormat);
        }
        return sDateFormat;
    }

    public static Date getDateFromString(String date, String pattern)
            throws ParseException {
        SimpleDateFormat sDateFormat = getDateFormat(pattern);

        synchronized (sDateFormat) {
            return sDateFormat.parse(date);
        }
    }

    public static Date getDefaultDateFromString(String date)
            throws ParseException {
        return getDateFromString(date, "yyyyMMddHHmmss");
    }

    public static String getNowDefault() {
        return getNow("yyyyMMddHHmmss");
    }

    public static String getNow(String pattern) {
        return getFormatTimeString(new Date(), pattern);
    }

    public static String getLastTime() {
        return LAST_TIME;
    }

    public static String getNowII() {
        return getFormatTimeString(new Date(), "yyyyMMdd");
    }

    public static long dateString2Long(String str, String pattern)
            throws ParseException {
        return getDateFromString(str, pattern).getTime();
    }

    /**
     * 校验字符串是否可以格式化为时间
     *
     * @param str
     * @param pattern
     * @return
     */
    public static boolean judgeDate(String str, String pattern) {
        try {
            dateString2Long(str, pattern);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String longToDateStringDefault(long time) {
        return getFormatTimeString(new Date(time), "yyyyMMddHHmmss");
    }

    public static String longToDateString(long time, String pattern) {
        return getFormatTimeString(new Date(time), pattern);
    }

    public static long date2Long(Date date) {
        return date.getTime();
    }

    public static Date longToDate(long time) {
        return new Date(time);
    }

    public static Date getDateFromStringAdaptTwoPattern(String date)
            throws ParseException {
        try {
            return getDateFromString(date, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
        }
        return getDateFromString(date, "yyyy-MM-dd");
    }

    public static String changeNumDateToDate(String numdate, String inFormat, String outFormat)
            throws ParseException {
        Date date = getDateFromString(numdate, inFormat);
        return getFormatTimeString(date, outFormat);
    }

    public static String getNextMonthFistDay(String nowdate, String inFormat, String outFormat)
            throws ParseException {
        Date date = getDateFromString(nowdate, inFormat);

        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.set(2, cl.get(2) + 1);
        cl.set(5, 1);

        date = cl.getTime();
        return getFormatTimeString(date, outFormat);
    }

    public static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }
        if (year % 4 == 0) {
            return (year % 100 != 0);
        }
        return false;
    }

    public static String getLastDay(String nowdate, String inFormat, String outFormat)
            throws ParseException {
        String returndate = "";

        Date date = getDateFromString(nowdate, inFormat);

        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        switch (cl.get(2)) {
            case 0:
                cl.set(5, 31);
                break;
            case 1:
                int year = cl.get(1);

                if (isLeapYear(year))
                    cl.set(5, 29);
                else {
                    cl.set(5, 28);
                }
                break;
            case 2:
                cl.set(5, 31);
                break;
            case 3:
                cl.set(5, 30);
                break;
            case 4:
                cl.set(5, 31);
                break;
            case 5:
                cl.set(5, 30);
                break;
            case 6:
                cl.set(5, 31);
                break;
            case 7:
                cl.set(5, 31);
                break;
            case 8:
                cl.set(5, 30);
                break;
            case 9:
                cl.set(5, 31);
                break;
            case 10:
                cl.set(5, 30);
                break;
            case 11:
                cl.set(5, 31);
        }

        date = cl.getTime();

        returndate = getFormatTimeString(date, outFormat);

        return returndate;
    }

    public static String getMonthLastDay(String fmt) {
        String returndate = "";
        Date date = null;
        Calendar cl = Calendar.getInstance();
        switch (cl.get(2)) {
            case 0:
                cl.set(5, 31);
                break;
            case 1:
                int year = cl.get(1);

                if (isLeapYear(year))
                    cl.set(5, 29);
                else {
                    cl.set(5, 28);
                }
                break;
            case 2:
                cl.set(5, 31);
                break;
            case 3:
                cl.set(5, 30);
                break;
            case 4:
                cl.set(5, 31);
                break;
            case 5:
                cl.set(5, 30);
                break;
            case 6:
                cl.set(5, 31);
                break;
            case 7:
                cl.set(5, 31);
                break;
            case 8:
                cl.set(5, 30);
                break;
            case 9:
                cl.set(5, 31);
                break;
            case 10:
                cl.set(5, 30);
                break;
            case 11:
                cl.set(5, 31);
        }

        date = cl.getTime();

        returndate = getFormatTimeString(date, fmt);

        return returndate;
    }

    public static String getNextMonthFirstDay(String fmt) {
        String returndate = "";
        Date date = null;

        Calendar cl = Calendar.getInstance();
        cl.set(2, cl.get(2) + 1);
        cl.set(5, 1);

        date = cl.getTime();

        returndate = getFormatTimeString(date, fmt);

        return returndate;
    }

    public static boolean compareDate(String fistDate, String secondDate, String format)
            throws ParseException {
        boolean flag = false;

        Date fist = null;
        Date second = null;

        fist = getDateFromString(fistDate, format);

        second = getDateFromString(secondDate, format);
        if (fist.before(second)) {
            flag = true;
        }

        return flag;
    }

    public static boolean isRightDate(String value, String varValue) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(varValue);
            format.setLenient(false);
            format.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }


    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static Date getCurrentMonthLastDay(Date day) {
        Calendar a = Calendar.getInstance();
        a.setTime(day);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.getTime();
    }

    /**
     * @param date     日期
     * @param dateType 模板类型
     * @param month    计数月
     * @return
     */
    public static String addMonth(String date, String dateType, int month) {
        String nowDate = null;
        SimpleDateFormat format = new SimpleDateFormat(dateType);
        try {
            Date parse = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            calendar.add(Calendar.MONTH, month);
            nowDate = format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nowDate;
    }

    public static String getHourMinute(String dateStr) throws ParseException {
        Date date = getDateFormat(DateUtil.DATE_FORMATE_STRING_A).parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String hourStr = hour < 10 ? "0" + hour : "" + hour;
        int minute = calendar.get(Calendar.MINUTE);
        String minuteStr = minute < 10 ? "0" + minute : "" + minute;

        return hourStr + ":" + minuteStr;

    }

    public static String getYyyyMmDdDate() {
        SimpleDateFormat tempDate = new SimpleDateFormat(DATE_FORMATE_STRING_H);
        String datetime = tempDate.format(new java.util.Date());
        return datetime;
    }

    /**
     * 比较时间是否跨天
     */
    public static boolean compareTimeSpansDay(String timeOne, String timeTwo) {
        try {
            Date dateOne = getDateFormat(DateUtil.DATE_FORMATE_STRING_A).parse("2020-01-01 " + timeOne + ":00");
            Date dateTwo = getDateFormat(DateUtil.DATE_FORMATE_STRING_A).parse("2020-01-01 " + timeTwo + ":00");
            Calendar calendarOne = Calendar.getInstance();
            calendarOne.setTime(dateOne);
            int hourOne = calendarOne.get(Calendar.HOUR_OF_DAY);
            Calendar calendarTwo = Calendar.getInstance();
            calendarTwo.setTime(dateTwo);
            int hourTwo = calendarTwo.get(Calendar.HOUR_OF_DAY);
            if (hourTwo < hourOne) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 给某一天加上一天
     */
    public static String addOneday(String today) {
        SimpleDateFormat f = new SimpleDateFormat(DATE_FORMATE_STRING_B);
        try {
            Date d = new Date(f.parse(today).getTime() + 24 * 3600 * 1000);
            return f.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return "时间格式有误";
        }
    }

    /**
     * 获取时分秒数的秒数
     */
    public static int getSecond(String time) {
        int s = 0;
        //时分秒格式00:00:00
        if (time.length() == 8) {
            int index1 = time.indexOf(":");
            int index2 = time.indexOf(":", index1 + 1);
            //小时
            s = Integer.parseInt(time.substring(0, index1)) * 3600;
            //分钟
            s += Integer.parseInt(time.substring(index1 + 1, index2)) * 60;
            //秒
            s += Integer.parseInt(time.substring(index2 + 1));
        }
        //分秒格式00:00
        if (time.length() == 5) {
            //秒  后两位肯定是秒
            s = Integer.parseInt(time.substring(time.length() - 2));
            //分钟
            s += Integer.parseInt(time.substring(0, 2)) * 60;
        }
        return s;
    }

    public static String getHourMinitSecond(long milliseconds) {
        StringBuilder sb = new StringBuilder();
        long mss = milliseconds / 1000;
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        if (hours > 0) {
            sb.append(hours).append("时").append(minutes).append("分").append(seconds).append("秒");
        } else if (minutes > 0) {
            sb.append(minutes).append("分").append(seconds).append("秒");
        } else {
            sb.append(seconds).append("秒");
        }
        return sb.toString();
    }

    /**
     * 获取昨天的日期字符串
     *
     * @return yyyy-MM-dd
     */
    public static String getYesterday() {
        LocalDate localDate = LocalDate.now();
        LocalDate yesterday = localDate.minusDays(1);
        return yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String getDateByMinusMouth(int mouthcout) {
        LocalDate localDate = LocalDate.now();
        LocalDate day = localDate.minusMonths(mouthcout);
        return day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    /**
     * 获取时间戳
     * type 为-1 表示获取昨天的日期，-2为前天的日期，以此类推。0 为今天的日期。
     */
    public static Timestamp strToDate(int type) {
        Timestamp time = null;
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.DATE, type);
        String tarday = new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime()) + " 00:00:00";

        // String转Date
        try {
            date = format2.parse(tarday);
            time = new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static List<String> getDateDiff(String startTime, String endTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startTime, fmt);
        LocalDate endDate = LocalDate.parse(endTime, fmt);
        List<String> dateMapList = new ArrayList<>();
        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            dateMapList.add(startDate.format(fmt));
            startDate = startDate.plusDays(1);
        }
        return dateMapList;
    }

    public static List<String> getMonthDiff(String startTime, String endTime) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(startTime));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(endTime));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 根据指定区间，获取对应区间内的每一天
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 区间内时间
     */
    public static List<String> findDays(String beginTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sdf.parse(beginTime);
            dEnd = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> daysStrList = new ArrayList<String>();
        daysStrList.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            String dayStr = sdf.format(calBegin.getTime());
            daysStrList.add(dayStr);
        }
        return daysStrList;
    }

    public static String dateToYmd(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 根据指定区间，获取对应区间内的每一天
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 区间内时间
     */
    public static List<String> findDays(Date beginTime, Date endTime) {
        String beginStr = dateToYmd(beginTime);
        String endStr = dateToYmd(endTime);
        return findDays(beginStr, endStr);
    }

    /**
     * 计算两个日期之间相差年月
     */
    public static String calculateTimeDifference(Date startDate, Date endDate) {
        if (null == startDate || null == endDate) {
            return "";
        }
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime fromDateTime = LocalDateTime.ofInstant(startDate.toInstant(), zoneId);
        LocalDateTime toDateTime = LocalDateTime.ofInstant(endDate.toInstant(), zoneId);

        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

        long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths(months);

        long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);
        return (0 == years ? "" : years + "年")
                + (0 == months ? "" : months + "月")
                + (0 == days ? "" : days + "天");
    }

    /**
     * 计算两个时间相差月数
     */
    public static long calculateMonthCount(Date startDate, Date endDate) {
        if (null == startDate || null == endDate) {
            return 0;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime fromDateTime = LocalDateTime.ofInstant(startDate.toInstant(), zoneId);
        LocalDateTime toDateTime = LocalDateTime.ofInstant(endDate.toInstant(), zoneId);

        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

        long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);

        return years * 12 + months;
    }

    /**
     * 计算两个日期相差天数
     */
    public static long calculateDayCount(String smdate,String bdate) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long between_days=0;
        try {
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days=(time2-time1)/(1000*3600*24);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return between_days;
    }
    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public static LocalDateTime parseToLocalDateTime(String str, String pattern) {
        if (StringUtils.isAnyBlank(str, pattern)) {
            return null;
        }
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception ex) {
            ex.printStackTrace();
            LocalDate localDate = parseLocalDate(str, pattern);
            localDateTime = Objects.isNull(localDate) ? null : localDate.atStartOfDay();
        }
        return localDateTime;
    }

    public static String LocalDateTimeToString(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || StringUtils.isEmpty(pattern)) {
            return null;
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMATE_STRING_B);
        return df.format(dateTime);
    }

    private static LocalDate parseLocalDate(String str, String pattern) {
        if (StringUtils.isAnyBlank(str, pattern)) {
            return null;
        }

        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return localDate;
    }

    /**
     * 返回上月日期串 兹自定义格式
     */
    public static String getLastMonthByFormat(String dateFormat) {
        LocalDate today = LocalDate.now();
        today = today.minusMonths(1);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(dateFormat);
        return formatters.format(today);
    }

    /**
     * 根据次数获取过去的年月日
     *
     * @param dateFormat 格式
     * @param num        过去几年
     * @return 日期
     */
    public static String getLastYearByFormat(String dateFormat, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -num);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        return format.format(date);
    }

    /**
     * 根据次数获取指定天数后的日期
     * @param dateFormat
     * @param num
     * @return
     */
    public static String getNeedDateByFormat(Date datetime, String dateFormat, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.add(Calendar.DATE, num);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        return format.format(date);
    }

    // 获取当前日期2个月后的时间
    public static String getTwoMonthAfter(String nowTime)throws ParseException{

        // 获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATE_STRING_A);
        Date date=dateFormat.parse(nowTime);

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, 2); //设置为前2月，可根据需求进行修改
        date = calendar.getTime();//获取2个月前的时间

        return dateFormat.format(date);
    }
    public static String formatDateTime(long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

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
        if (StrUtil.isEmpty(sb.toString()) && milliSecond > 0) {
            sb.append("0秒");
        }
        return sb.toString();
    }

    /**
     * 计算两个时间相差年数
     */
    public static long calculateYearCount(Date startDate, Date endDate) {
        if (null == startDate || null == endDate) {
            return 0;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime fromDateTime = LocalDateTime.ofInstant(startDate.toInstant(), zoneId);
        LocalDateTime toDateTime = LocalDateTime.ofInstant(endDate.toInstant(), zoneId);

        long year = fromDateTime.until(toDateTime, ChronoUnit.YEARS);
        return year == 0 ? 1 : year + 1;
    }

    public static int monthInterval(Date begin, Date end){
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(begin);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);

        int endYear = endCal.get(Calendar.YEAR);
        int beginYear = beginCal.get(Calendar.YEAR);

        int endMonth = endCal.get(Calendar.MONTH);
        int beginMonth = beginCal.get(Calendar.MONTH);

        return (endYear - beginYear) * 12 + endMonth - beginMonth + 1;
    }

    /**
     * 获取指定日期是当年的第几天
     */
    public static int dayOfYear(Date date) {
        if (date == null) {
            return 1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static void main(String[] args) throws ParseException {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, 365);
        int thisYear = now.get(Calendar.YEAR);
        int thisMonth = now.get(Calendar.MONTH) + 1;
        int thisDay = now.get(Calendar.DAY_OF_MONTH);
        System.out.println(thisYear);
        System.out.println(thisMonth);
        System.out.println(thisDay);
        System.out.println(getNeedDateByFormat(new Date(), DATE_FORMATE_STRING_N, 45));
    }
}
