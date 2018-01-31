package com.xiaoka.cloud.stock.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateFormatUtil {
	private static final Log LOG = LogFactory.getLog(DateFormatUtil.class);

	public static final String TIME_FORMAT_A = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT_B = "yyyyMMddHHmmss";
	public static final String TIME_FORMAT_C = "yyyy-MM-dd HH:mm:ss:SSS";
	public static final String TIME_FORMAT_D = "yyyyMMdd";
	public static final String TIME_FORMAT_E = "yyyy年MM月dd日";
	public static final String TIME_FORMAT_F = "yyyyMMddHHmm";
	public static final String TIME_FORMAT_G = "yyyy年MM月dd日HH时mm分ss秒";
	public static final String TIME_FORMAT_H = "yyyy-MM-dd HH:mm";
	public static final String TIME_FORMAT_I = "HH:mm:ss";
	public static final String TIME_FORMAT_J = "yyyyMMddHHmmssSSS";
	public static final String TIME_FORMAT_K = "yy-M-d";
	public static final String TIME_FORMAT_L = "HH:mm";
	public static final String TIME_FORMAT_M = "MM月dd日 HH:mm";
	public static final String TIME_FORMAT_N = "yyyyMM";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_H = "yyyy-MM-dd HH";
	public static final String YEAR_FORMAT = "yyyy";
	public static final String YEAR_MONTH_FORMAT = "yyyy-MM";
	public static final String MONTH_DAY_FORMAT = "MM-dd";
	public static final String MONTH_DAY_HOUR_MINUTE_FORMAT = "MM-dd HH:mm";
	public static final String FORMAT_1 = ",##0.00";
	public static final String FORMAT_2 = "0.00";
	public static final String FORMAT_3 = ",###";
	public static final String FIRST_TIME = "1970-01-01 00:00:00";

	public static final String TIME_FORMAR_RY = "MM/dd/yyyy HH:mm:ss";

	/**
	 * 返回今天的日期字符串 <br>
	 * 2015-09-24
	 *
	 * @return
	 */
	public static String timeDay() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 时间类型转化为格式化字符串
	 *
	 * @param date   时间
	 * @param format 格式化样式
	 * @return 格式化字符串，如果失败返回为null
	 */
	public static String date2String(Date date, String format) {
		String dateStr = null;
		try {
			if (date != null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
				dateStr = simpleDateFormat.format(date);
			}
		} catch (Exception ex) {
			LOG.error("date to string failure. The detail information is: ", ex);
		}
		return dateStr;
	}

	/**
	 * 格式化字符串转化为时间类型
	 *
	 * @param dateStr 格式化字符串
	 * @param format  格式化样式
	 * @return 时间类型，如果失败返回为null
	 */
	public static Date string2Date(String dateStr, String format) {
		if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format)) {
			return null;
		}
		Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(dateStr);
		} catch (ParseException ex) {
			LOG.error("string to date failure. The detail information is ", ex);
		}
		return date;
	}

	/**
	 * 得到当天的第一微秒的时间。 <br>
	 * 如：传入参数是2011-03-10 11:25:33， 返回的时间是 2011-03-10 00:00:00:000
	 *
	 * @param date
	 * @return
	 */
	public static Date getBeginningOfTheDay(Date date) {
		if (null == date) {
			return null;
		}
		return DateFormatUtil.string2Date(
				DateFormatUtil.date2String(date, DATE_FORMAT), DATE_FORMAT);
	}

	/**
	 * 得到当天的最后一微秒的时间。 <br>
	 * 如：传入参数是2011-03-10 11:25:33， 返回的时间是 2011-03-10 23:59:59:999
	 *
	 * @param date
	 * @return
	 */
	public static Date getEndOfTheDay(Date date) {
		if (null == date) {
			return null;
		}
		Date beginningOfTheDay = DateFormatUtil.getBeginningOfTheDay(date);
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(beginningOfTheDay.getTime() + 24 * 60 * 60
				* 1000 - 1);
		return calendar.getTime();
	}

	/**
	 * 判断两个日期之间相差多少天（忽略时分秒）
	 *
	 * @param firstDate   第一个指定的日期
	 * @param anotherDate 第二个指定的日期
	 * @return 相差的天数
	 */
	public static int minusWithDay(Date firstDate, Date anotherDate) {
		long first = DateUtils.truncate(firstDate, Calendar.DAY_OF_MONTH)
		                      .getTime();
		long another = DateUtils.truncate(anotherDate, Calendar.DAY_OF_MONTH)
		                        .getTime();
		return (int) ((first - another) / (24 * 60 * 60 * 1000));
	}

	public static double hourTimeDiff(Date d1, Date d2) {
		assertDateParamsRequired(d1, d2);
		return (double) dateMilliSecondDiff(d1, d2) / (double) (1000 * 60 * 60);
	}

	public static double secondTimeDiff(Date d1, Date d2) {
		assertDateParamsRequired(d1, d2);
		return (double) dateMilliSecondDiff(d1, d2) / (double) 1000;
	}

	public static long dateMilliSecondDiff(Date d1, Date d2) {
		long millis1 = d1.getTime();
		long millis2 = d2.getTime();
		return millis1 - millis2;
	}

	/**
	 * 按日偏移,计算source指定日期的days天后的日期<Br>
	 *
	 * @param source , 要求非空
	 * @param days   , 天数,可以为负
	 * @return 新创建的Date对象
	 * @author michael.yangf
	 */
	public static Date addDays(Date source, int days) {
		return addDate(source, Calendar.DAY_OF_MONTH, days);
	}

	// Add Date并且返回一个新的日历对象
	private static Date addDate(Date date, int calendarField, int amount) {
		assertDateParamsRequired(date);

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	private static void assertDateParamsRequired(Date... dates) {
		for (Date date : dates) {
			if (date == null) {
				throw new IllegalArgumentException(
						"The Date type parameters are required.");
			}
		}
	}

	/**
	 * 时间格式处理
	 *
	 * @param date
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String getSimpleDateForPhone(Date date) {
		if (minusWithDay(new Date(), date) == 0) {
			return date2String(date, TIME_FORMAT_L);
		} else {
			return date2String(date, TIME_FORMAT_K);
		}
	}

	public static String getUnifyFormat(Date date) {
		if (date == null) {
			return "";
		}
		Date now = new Date();
		if (DateUtils.isSameDay(now, date)) {
			return date2String(date, TIME_FORMAT_L);
		} else if (DateUtils.truncatedCompareTo(now, date, Calendar.YEAR) == 0) {
			return date2String(date, MONTH_DAY_FORMAT);
		} else {
			return date2String(date, DATE_FORMAT);
		}
	}

	public static String getUnifyFormatForSingleShopActivity(Date date) {
		if (date == null) {
			return "";
		}
		Date now = new Date();
		if (DateUtils.isSameDay(now, date)) {
			return date2String(date, TIME_FORMAT_L);
		} else if (DateUtils.truncatedCompareTo(now, date, Calendar.YEAR) == 0) {
			return date2String(date, MONTH_DAY_HOUR_MINUTE_FORMAT);
		} else {
			return date2String(date, TIME_FORMAT_H);
		}
	}

	/**
	 * 获取 1970-01-01 00:00:00 〈功能详细描述〉
	 *
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static Date getFirstDate() {
		return string2Date(FIRST_TIME, TIME_FORMAT_A);
	}

	public static Date getLaterDate(int field, int unit, Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		c.set(field, c.get(field) + unit);
		c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	public static Date getLastDayBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getLastDayEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date getPreviousDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}

	public static int getMonthDiff(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		int y1 = c1.get(Calendar.YEAR);
		int m1 = c1.get(Calendar.MONTH);

		int y2 = c2.get(Calendar.YEAR);
		int m2 = c2.get(Calendar.MONTH);

		return Math.abs((y1 - y2) * 12 + (m1 - m2));
	}

	public static int getDaysDiff(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		Long between_days = (c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000 * 3600 * 24);

		return Math.abs(between_days.intValue());
	}

}
