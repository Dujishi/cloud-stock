package com.xiaoka.cloud.stock.service.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author gancao 2017/9/12 19:13
 * @see [相关类/方法]
 * @since [版本号]
 */
public class DateUtils {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_NEW = "yyyy.MM.dd";
	/**
	 * ES中存储的日期格式2017-09-13T06:35:12+08:00
	 */
	public static String DATE_FORMAT_ES = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	/**
	 * 日期格式转换
	 * @param date
	 * @return
	 */
	public static String parse(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

	/**
	 * 获取今天结束时间
	 *
	 * @return
	 */
	public static Date getStartTimeOfToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.getStartTimeOfToday());
	}
}
