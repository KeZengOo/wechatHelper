package com.nuoxin.virtual.rep.api.utils.excel;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间工具类
 * 
 * @author song
 */
public class DateUtil {

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	private static final String yyyyMMdd = "yyyyMMdd";
	private static final String yyyyMMddHH = "yyyyMMddHH";
	private static final String yyyy_MM_dd = "yyyy-MM-dd";

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat(DEFAULT_PATTERN);
	private static SimpleDateFormat YYYYMMDD_FORMAT = new SimpleDateFormat(yyyyMMdd);
	private static SimpleDateFormat YYYY_MM_DD_FORMAT = new SimpleDateFormat(yyyy_MM_dd);
	private static SimpleDateFormat yyyyMMddHH_FORMAT = new SimpleDateFormat(yyyyMMddHH);
	
	public static SimpleDateFormat getSimpleDateFormat(String pattern){
		return new SimpleDateFormat(pattern);
	}

	public static Date getCurrentTime() {
		return new Date();
	}

	/**
	 * 转成默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		if (date == null) {
			return null;
		}
		return DEFAULT_FORMAT.format(date);
	}

	/**
	 * 将Date类型的日期转换为参数定义的格式的字符串。
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null || pattern == null) {
			return null;
		}
		if (pattern.equalsIgnoreCase(yyyyMMdd)) {
			return YYYYMMDD_FORMAT.format(date);
		}
		if (pattern.equalsIgnoreCase(yyyy_MM_dd)) {
			return YYYY_MM_DD_FORMAT.format(date);
		}
		if (pattern.equalsIgnoreCase(yyyyMMddHH)) {
			return yyyyMMddHH_FORMAT.format(date);
		}
		SimpleDateFormat dateFromat = new SimpleDateFormat(pattern);
		return dateFromat.format(date);
	}

	public static Date parse(String time, String pattern) {
		if(StringUtils.isBlank(time)){
			return null;
		}
		try {
			if (pattern == null || time == null) {
				return null;
			}
			if (pattern.equalsIgnoreCase(yyyyMMdd)) {
				return YYYYMMDD_FORMAT.parse(time);
			}
			if (pattern.equalsIgnoreCase(yyyy_MM_dd)) {
				return YYYY_MM_DD_FORMAT.parse(time);
			}
			if (pattern.equalsIgnoreCase(yyyyMMddHH)) {
				return yyyyMMddHH_FORMAT.parse(time);
			}
			SimpleDateFormat dateFromat = new SimpleDateFormat(pattern);
			return dateFromat.parse(time);
		} catch (Exception e) {
			logger.info("", e);
		}
		return null;
	}

	public static Date yesterday(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		return cal.getTime();
	}
	
	public static Date yesterday(Date date){
		Calendar   cal   =   Calendar.getInstance();
		cal.setTime(date);
		return cal.getTime();
	}

	public static Date yesterdayStart(Date date){
		Calendar   cal   =   Calendar.getInstance();
		if(date!=null){
			cal.setTime(date);
			cal.add(Calendar.DATE,   -1);
		}else{
			cal.add(Calendar.DATE,   -1);
		}
		String startTimeStr = getSimpleDateFormat("yyyy-MM-dd 00:00:00").format(cal.getTime());
		Date startTiem =DateUtil.parse(startTimeStr, DEFAULT_PATTERN);
		return startTiem;
	}

	public static Date yesterdayEnd(Date date){
		Calendar   cal   =   Calendar.getInstance();
		if(date!=null){
			cal.setTime(date);
			cal.add(Calendar.DATE,   -1);
		}else{
			cal.add(Calendar.DATE,   -1);
		}
		String endTimeStr = getSimpleDateFormat("yyyy-MM-dd 23:59:59").format(cal.getTime());
		Date endTiem =DateUtil.parse(endTimeStr, DEFAULT_PATTERN);
		return endTiem;
	}
}
