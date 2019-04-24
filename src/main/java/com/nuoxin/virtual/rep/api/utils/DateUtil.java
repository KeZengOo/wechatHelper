package com.nuoxin.virtual.rep.api.utils;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 对时间做处理的工具类
 *
 * @author caoxuodng
 * <p>
 * 2014年4月17日
 */
public final class DateUtil {

    private DateUtil() {
    }

    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS__SSS = "yyyy-MM-dd HH:mm:ss:SSS";

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final DateFormat DATE_FORMAT_YMR = new SimpleDateFormat("yyyy-MM-dd");

    public static final DateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final DateFormat DATE_FORMAT_MILLISECONDTIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static final DateFormat DATE_FORMAT_SECONDTIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final DateFormat DATE_FORMAT_YM = new SimpleDateFormat("yyyy-MM");

    public static final DateFormat DATE_FORMAT_MONTH = new SimpleDateFormat("yyyyMM");

    public static final DateFormat DATE_FORMAT_YMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 返回上个月份日期格式：yyyyMM
     *
     * @param date
     * @return
     */
    public static String getDateMonthString(DateFormat dateFormat, Date date, int month) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.MONTH, month);
        return dateFormat.format(ca.getTime());
    }

    /**
     * 返回日期格式：yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_FORMAT_YMR.format(date);
    }

    /**
     * 返回日期格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTimeString(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * 返回日期格式：yyyyMMddHHmmssSSS
     *
     * @param date
     * @return
     */
    public static String getDateMillisecondString(Date date) {
        return DATE_FORMAT_MILLISECONDTIME.format(date);
    }


    /**
     * 返回日期格式：yyyyMMddHHmmssSSS
     *
     * @param date
     * @return
     */
    public static String getDateSecondString(Date date) {
        return DATE_FORMAT_TIME.format(date);
    }


    /**
     * 将Date转化为指定格式的字符串
     *
     * @param specialDate
     * @param dateFormat
     * @return
     */
    public static String gettDateStrFromSpecialDate(Date specialDate, String dateFormat) {
        if (specialDate == null) {
            return "";
        }
        String strCurrentTime = "";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        strCurrentTime = formatter.format(specialDate);
        return strCurrentTime;
    }

    /**
     * 获取指定时间上N分钟后的时间
     *
     * @param oldDate
     * @param dateFormat
     * @param intervalMinute
     * @return
     */
    public static String getSpecialDateByIntervalMinute(Date oldDate, String dateFormat,
                                                        int intervalMinute) {

        long time = oldDate.getTime() + intervalMinute * 60 * 1000;
        Date preDate = new Date(time);

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        return formatter.format(preDate);
    }


    /**
     * 获取两个日期之间的日期集合
     * @param start
     * @param end
     * @return
     */
    public static  List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }


    /**
     * 获取两个日期之间的日期集合， yyyy-MM-dd格式
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }


    /**
     * 获取两个日期之间的日期天数
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDayCount(String startTime, String endTime) {

        List<String> days = getDays(startTime, endTime);
        int size = days.size();
        if (size >=1){
            size = size - 1;
        }
        return size;
    }


    /**
     * 得到周六日和法定节假日天数
     * @param startTime
     * @param endTime
     * @param holidayStr
     * @return
     */
    public static int getDaysWeekend(String startTime, String endTime, List<String> holidayStr) {

        if (holidayStr == null){
            holidayStr = new ArrayList<>();
        }

        // 返回的日期集合
        int days = 0;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
//            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            List<Calendar> holidayList = getHolidayList(holidayStr);
            while (tempStart.before(tempEnd)) {
                // 判断是否是周六日
                if (tempStart.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || tempStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    days ++;

                }else {// 法定节假日
                    String dateStr = DATE_FORMAT_YMR.format(tempStart.getTime());
                    if(holidayStr.contains(dateStr)){
                        days ++;
                    }
                }
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }


    public static void main(String[] args) {
        List<String> holiday = new ArrayList<>();
        holiday.add("2019-01-10");
        holiday.add("2019-01-11");
        holiday.add("2019-01-12");
        holiday.add("2019-01-13");
        int daysRemoveWeekend = DateUtil.getDaysWeekend("2018-11-28", DateUtil.gettDateStrFromSpecialDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD), holiday);
        int dayCount = DateUtil.getDayCount("2019-01-13", "2019-01-14");
        System.out.println(daysRemoveWeekend);
        System.out.println(dayCount);
    }


    /**
     * 得到节假日
     * @param holidayStr
     * @return
     */
    private static List<Calendar> getHolidayList(List<String> holidayStr){
        if (CollectionsUtil.isEmptyList(holidayStr)){
            return new ArrayList<>();
        }


        List<Calendar> holidayList = new ArrayList<>();

        for (String string : holidayStr) {
            String[] da = string.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(da[0]));
            calendar.set(Calendar.MONTH, Integer.valueOf(da[1]) - 1);// 月份比正常小1,0代表一月
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(da[2]));
            holidayList.add(calendar);
        }


        return holidayList;

    }


    /**
     *  java 获取 获取某年某月 所有日期（yyyy-mm-dd格式字符串）
     * @param year
     * @param month
     * @return
     */
    public static List<String> getMonthFullDay(int year , int month){
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        List<String> fullDayList = new ArrayList<>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month-1 );
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count ; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        return fullDayList;
    }


    /**
     * 取得当月天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    /**
     * 获取当月的第一天的日期
     *
     * @return
     * @throws ParseException
     */
    public static Date getTheFirstDayInCurrentMonth() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        SimpleDateFormat dateTimeFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
        String str = dateFormat.format(calendar.getTime());
        str = str + " 00:00:00.000";
        return dateTimeFormat.parse(str);
    }

    /**
     * 获取当月最后一天的日期对象
     *
     * @return
     * @throws ParseException
     */
    public static Date getTheLastDayInCurrentMonth() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        SimpleDateFormat dateTimeFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
        String str = dateFormat.format(calendar.getTime());
        str = str + " 23:59:59.999";
        return dateTimeFormat.parse(str);
    }

    /**
     * 获取昨天最后一毫秒
     *
     * @return
     * @throws ParseException
     */
    public static Date getLastMilliSecondAtYesterday() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        SimpleDateFormat dateTimeFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
        String str = dateFormat.format(calendar.getTime());
        str = str + " 23:59:59.999";
        return dateTimeFormat.parse(str);
    }

    /**
     * 获取当前日期指定相隔的月份
     *
     * @param apartMonth 相隔的月份
     * @return
     */
    public static String getCertainMonthByCurrentDate(DateFormat dateFormat, int apartMonth) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, apartMonth);
        return dateFormat.format(c.getTime());
    }

    /**
     * 获取相隔当前时间一定天数的日期
     *
     * @param apartDay 相隔的月份
     * @return
     */
    public static String getCertainDayByCurrentDate(DateFormat dateFormat, int apartDay) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, apartDay);
        return dateFormat.format(c.getTime());
    }

    /**
     * 根据字符串，获取指定的日期
     *
     * @param dateString
     * @param dateFormat
     * @return
     */
    public static Date stringToDate(String dateString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileFormatException(ErrorEnum.ERROR, "传入的日期不合法");
        }

    }

    /**
     * 根据时间获取当天23:59:59的时间
     *
     * @param date
     * @return
     */
    public static Date get24Hour(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfDay.format(date);
        return getDateFromStr(dateStr + " 23:59:59");
    }

    /**
     * 根据目标日期获取当天凌晨的时间
     *
     * @param date
     * @return
     */
    public static Date getFirstSecond(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfDay.format(date);
        return getDateFromStr(dateStr + " 00:00:00");
    }

    /**
     * 按照格式"yyyy-MM-dd HH:mm:ss"，将字符串解析为日期对象
     *
     * @param date
     * @return
     */
    public static Date getDateFromStr(String date) {
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return ft.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期格式的字符串
     *
     * @param dateFormat
     * @return
     */
    public static String getDateStr(DateFormat dateFormat) {
        try {
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 在指定的日期上加上day天
     *
     * @param curr 指定的日期
     * @param day  需要加的天数
     * @return
     */
    public static Date addDay(Date curr, int day) {
        if (curr == null) {
            curr = new Date();
        }
        return new Date(curr.getTime() + 1000L * 3600 * 24 * day);
    }

    private static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 封装开始时间
     *
     * @param date
     * @return
     */
    public static Date beginDate(Date date) {
        if (null == date) {
            return null;
        }
        String beginDate = format1.format(date) + " 00:00:00";
        try {
            return format2.parse(beginDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 封装结束时间
     *
     * @param date
     * @return
     */
    public static Date endDate(Date date) {
        if (null == date) {
            return null;
        }
        String endDate = format1.format(date) + " 23:59:59";
        try {
            return format2.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String transSecondToLong(Integer seconds) {
        String timeLong = "";
        if (seconds == null) {
            seconds = 0;
        }
        if (seconds < 60) {
            timeLong = seconds + "秒";
        } else if (seconds >= 60 && seconds < 60 * 60) {
            timeLong = seconds / 60 + "分";
        } else if (seconds >= 60 * 60 && seconds < 60 * 60 * 24) {
            timeLong = seconds / 60 / 60 + "小时";
        } else if (seconds >= 60 * 60 * 24 && seconds < 60 * 60 * 24 * 30) {
            timeLong = seconds / 60 / 60 / 24 + "天";
        } else if (seconds >= 60 * 60 * 24 * 30 && seconds < 60 * 60 * 24 * 30 * 12) {
            timeLong = seconds / 60 / 60 / 24 / 30 + "月";
        } else if (seconds >= 60 * 60 * 24 * 30 * 12) {
            timeLong = seconds / 60 / 60 / 24 / 30 / 12 + "年";
        }

        return timeLong;
    }


    public static String transSecondToLong(Long seconds, Date date) {
        String timeLong = "";
        if (seconds == null) {
            seconds = 0l;
        } else {
            seconds = seconds / 1000;
        }

        if (seconds >= 60 && seconds < 60 * 60) {
            timeLong = seconds / 60 + "分前";
        } else if (seconds >= 60 * 60 && seconds < 60 * 60 * 24) {
            timeLong = seconds / 60 / 60 + "小时前";
        } else if (seconds >= 60 * 60 * 24 && seconds < 60 * 60 * 24 * 30) {
            timeLong = seconds / 60 / 60 / 24 + "天前";
        } else {
            timeLong = DATE_FORMAT_YMDHM.format(date);
        }

        return timeLong;
    }

    public static Long getBeforeDay(Date date) {
        Date d = new Date(date.getTime() - 1000 * 60 * 60 * 24);
        SimpleDateFormat sp = new SimpleDateFormat("yyyyMMdd");
        return new Long(sp.format(d));
    }

    public static Long getDayAfter(Date date) {
        Calendar cdate = Calendar.getInstance();
        cdate.setTime(date);
        cdate.add(Calendar.DATE, 1);
        cdate.set(Calendar.HOUR_OF_DAY, 0);
        cdate.set(Calendar.MINUTE, 0);
        cdate.set(Calendar.SECOND, 0);
        cdate.set(Calendar.MILLISECOND, 0);
        Date ldate = cdate.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyyMMdd");
        return new Long(sp.format(ldate));
    }

    public static Date getYearMonthStartDate_M(Integer year, Integer month) {
        Calendar cdate = Calendar.getInstance();
        cdate.clear();
        cdate.set(Calendar.YEAR, year);
        cdate.set(Calendar.MONTH, month - 1);
        cdate.set(Calendar.DATE, 1);
        cdate.set(Calendar.HOUR_OF_DAY, 0);
        cdate.set(Calendar.MINUTE, 0);
        cdate.set(Calendar.SECOND, 0);
        cdate.set(Calendar.MILLISECOND, 0);
        return cdate.getTime();
    }

    public static Date getYearMonthEndDate_M(Integer year, Integer month) {
        Calendar cdate = Calendar.getInstance();
        cdate.clear();
        cdate.set(Calendar.YEAR, year);
        cdate.set(Calendar.MONTH, month - 1);
        cdate.set(Calendar.DATE, 1);
        cdate.set(Calendar.HOUR_OF_DAY, 0);
        cdate.set(Calendar.MINUTE, 0);
        cdate.set(Calendar.SECOND, 0);
        cdate.set(Calendar.MILLISECOND, 0);
        cdate.add(Calendar.MONTH, 1);
        return cdate.getTime();
    }

    public static Date getYearMonthStartDate_Q(Integer year, Integer quarter) {
        Calendar cdate = Calendar.getInstance();
        cdate.clear();
        cdate.set(Calendar.YEAR, year);
        if (quarter == 1) {
            cdate.set(Calendar.MONTH, 0);
        } else if (quarter == 2) {
            cdate.set(Calendar.MONTH, 3);
        } else if (quarter == 3) {
            cdate.set(Calendar.MONTH, 6);
        } else {
            cdate.set(Calendar.MONTH, 9);
        }
        cdate.set(Calendar.DATE, 1);
        cdate.set(Calendar.HOUR_OF_DAY, 0);
        cdate.set(Calendar.MINUTE, 0);
        cdate.set(Calendar.SECOND, 0);
        cdate.set(Calendar.MILLISECOND, 0);
        return cdate.getTime();
    }

    public static Date getYearMonthEndDate_Q(Integer year, Integer quarter) {
        Calendar cdate = Calendar.getInstance();
        cdate.clear();
        cdate.set(Calendar.YEAR, year);
        if (quarter == 1) {
            cdate.set(Calendar.MONTH, 2);
        } else if (quarter == 2) {
            cdate.set(Calendar.MONTH, 5);
        } else if (quarter == 3) {
            cdate.set(Calendar.MONTH, 8);
        } else {
            cdate.set(Calendar.MONTH, 11);
        }
        cdate.set(Calendar.DATE, 1);
        cdate.set(Calendar.HOUR_OF_DAY, 0);
        cdate.set(Calendar.MINUTE, 0);
        cdate.set(Calendar.SECOND, 0);
        cdate.set(Calendar.MILLISECOND, 0);
        cdate.add(Calendar.MONTH, 1);
        return cdate.getTime();
    }

    /**
     * 得到两个时间之间相差的秒数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int calLastedTime(Date startDate, Date endDate) {
        long a = endDate.getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }


    /**
     * 得到两个时间之间相差的秒数
     * @param startTime
     * @param endTime
     * @return
     */
    public static int calLastedTime(String startTime, String endTime) {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            int time = calLastedTime(startDate, endDate);
            return time;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }




}
