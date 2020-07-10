package com.yqkj.components.dtransaction.utils;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式化组件，线程安全<br>
 *
 * @author lx
 *
 */
public class DateUtil {

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String HOUR_PATTERN = "yyyy-MM-dd HH";

	public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String SIMPLE_PATTERN = "yyyyMMddHHmmss";

	/**
	 * 获取Java起始时间
	 * @return
	 */
	public static Date originTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(0L);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date current() {
		return new Date();
	}

	/**
	 * 获取当天的零时时间
	 * @return
	 */
	public static Date currentZero() {
		return toZeroDate(current());
	}

	/**
	 * 格式化日期，输出字符格式为yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return FastDateFormat.getInstance(PATTERN).format(date);
	}

	/**
	 * 格式化日期，按yyyy-MM-dd HH:mm:ss格式进行转换
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String date) throws ParseException {
		return FastDateFormat.getInstance(PATTERN).parse(date);
	}

	public static String format(String pattern, Date date) {
		return FastDateFormat.getInstance(pattern).format(date);
	}

	public static Date parse(String pattern, String date) throws ParseException {
		return FastDateFormat.getInstance(pattern).parse(date);
	}

	public static Date stringToDate(String pattern,String dateString) throws ParseException {
		return new SimpleDateFormat(pattern).parse(dateString);
	}
	/**
	 * 设置时间为零点<br>
	 * 00:00:00:000<br>
	 *
	 * @param date
	 * @return
	 */
	public static Date toZeroDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	return c.getTime();
	}
	/**
	 * 获取下一个月或上个月的日期<br>
	 * 当前月为31号，下个月无31号，则取下个月最大天数，例如2月可能取28，29号，其他月可能30号<br>
	 *
	 * @param current
	 * @param nextMonth
	 * @return
	 */
	public static Date nextMonthCycle(Date current, int nextMonths) {
		Calendar c = Calendar.getInstance();
		c.setTime(current);
		c.add(Calendar.MONTH, nextMonths);
		return c.getTime();
	}

	/**
	 * 计算两个时间相差天数，两个时间都先设置为零点后再计算<br>
	 * end比start时间小，则返回负值<br>
	 *
	 */
	public static int dayBetween(Date start, Date end) {
		Date _start = toZeroDate(start);
		Date _end = toZeroDate(end);
		return (int) ((_end.getTime() - _start.getTime())/(24*60*60*1000));
	}

	/**
	 * 获取下一个周期的日期<br>
	 *
	 * @param current
	 * @param nextMonth
	 * @return
	 */
	public static Date nextDayCycle(Date current, int nextDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(current);
		c.add(Calendar.DATE, nextDays);
		return c.getTime();
	}

	/**
	 * 获取下一个时间
	 * @param current 当前时间
	 * @param second 秒
	 * @return
	 */
	public static Date nextTime(Date current, int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(current);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}

	/**
	 * 日期加上天数的时间
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date dateAddDay(Date date, int day)
	{
		return add(date, Calendar.DAY_OF_YEAR, day);
	}
	private static Date add(Date date, int type, int value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}

	/**
	 * 根据毫秒返回时间对象
	 * @param time
	 * @return
	 */
	public static Date toDate(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.getTime();
	}

	/*public static void main(String[] args) throws ParseException {
		Date current = parse("yyyy-MM-dd HH:mm:ss", "2019-03-31 05:05:12");
		System.out.println(format(toZeroDate(nextMonthCycle(current, -1))));


		Date start = parse("yyyy-MM-dd", "2019-03-01");
		Date end = parse("yyyy-MM-dd", "2019-03-05");
		System.out.println(-4 == dayBetween(start, end));

		System.out.println(DateUtil.format(DateUtil.originTime()));
	}*/
}
