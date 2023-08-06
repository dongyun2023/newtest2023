package com.game.yangtechplatform.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;

public class TimeUtils {
	public static String YMDHFM = "yyyy-MM-dd HH:mm:ss";
	public static String YMD = "yyyy-MM-dd";

	/**
	 * 转换long型日期格式
	 * 
	 * @param context
	 * @param date
	 * @return
	 */
	public static String formatDate(Context context, long date) {
		int format_flags = DateUtils.FORMAT_NO_NOON_MIDNIGHT
				| DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_CAP_AMPM
				| DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_DATE
				| DateUtils.FORMAT_SHOW_TIME;
		return DateUtils.formatDateTime(context, date, format_flags);
	}

	/**
	 * 
	 * @return 获取30天的毫秒时间
	 */
	public static Long getMonthToMine() {
		return 30 * 24 * 60 * 60 * 1000L;
	}

	/**
	 * 转换long型日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(date));
	}

	/**
	 * 转换long型日期格式 年月日时分秒
	 * 
	 * @param date
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateYMDHFM(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(date));
	}

	/**
	 * 获取当天的开始时间
	 * 
	 * @return
	 */
	public static String getThisDateBeginTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return format.format(currentDate.getTime());
	}

	/**
	 * 获取当前时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getThisDate() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 获取当前的时间
	 * 
	 * @param context
	 * @return
	 */
	public static String getTime(Context context) {
		return formatDate(context, System.currentTimeMillis());
	}

	/**
	 * 获取当前的时间
	 * 
	 * @return
	 */
	public static String getTime() {
		return formatDate(System.currentTimeMillis());
	}

	/**
	 * 获取当前月的第一天
	 * 
	 * @return
	 */
	public static String getThisMonthFirstDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String first = format.format(c.getTime());
		return first;
	}

	/**
	 * //获取当前月最后一天
	 */
	public static String getThisMonthLastDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}

	public static String getThisWeekFirstDay() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		String first = df.format(cal.getTime());
		return first;
	}

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss SSS");
	}

	// "yyyy-MM-dd"
	public static Date getDateToString(String date, String format) {
		try {
			DateFormat fmt = new SimpleDateFormat(format);
			Date date1 = fmt.parse(date);
			return date1;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将string日期转换成long
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Long getStringTolong(String date, String format) {
		try {
			DateFormat fmt = new SimpleDateFormat(format);
			Date date1 = fmt.parse(date);
			return date1.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0L;
		}
	}

	/**
	 * 设置系统时间
	 * 
	 * @param currentTime
	 *            【时间格式 yyyy-MM-dd HH:mm:ss】 测试出可设置范围是1970-01-01到2038-01-18
	 * @return 是否成功
	 */

	public static boolean setSystemDate(String currentTime) {
		try {
			Process process = Runtime.getRuntime().exec("su");
			Date tempDate = StrToDate(currentTime);
			currentTime = DateToStr(tempDate);
			DataOutputStream os = new DataOutputStream(
					process.getOutputStream());
			os.writeBytes("setprop persist.sys.timezone GMT\n");
			os.writeBytes("/system/bin/date -s " + currentTime + "\n");// 测试的设置的时间【时间格式// yyyyMMdd.HHmmss】
			os.writeBytes("clock -w\n");
			os.writeBytes("exit\n");
			os.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转换成Java字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd.HHmmss");
		String str = format.format(date);
		return str;
	}
}
