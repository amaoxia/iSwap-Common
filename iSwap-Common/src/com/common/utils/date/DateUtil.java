package com.common.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/**
 * 对日期的处理
 * 
 * @Company 北京光码软件有限公司
 * @author hudaowan
 * @version iSwap V5.0
 * @date Aug 5, 2008 3:43:24 PM
 * @Team 数据交换平台研发小组
 */
public class DateUtil {
	private static String previousDateTime;
	public synchronized static String getUniqueDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateTime = sdf.format(new Date());
		while (dateTime.equals(previousDateTime)) {
			dateTime = sdf.format(new Date());
		}
		previousDateTime = dateTime;
		return dateTime;
	}

	/**
	 * 将当前系统时间转换成直至Millisecond的样式
	 */
	public static String getDateTimeZone() {
		return new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
	}

	/**
	 * 将当前系统时间转换成直至HHmmssS的样式
	 * 
	 * @author hudaowan
	 * @date 2008-4-3 上午09:48:56
	 * @return
	 */
	public static String getTimeZone() {
		return new SimpleDateFormat("HHmmssS").format(new Date());
	}

	/**
	 * 根据时间格式取当前时间
	 * 
	 * @author hudaowan 2006-10-19 下午01:58:34
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 得到当前系统时间和日期
	 * 
	 * @author hudaowan 2006-10-13 上午09:55:24
	 * @return
	 */
	public static String getDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String getDateTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 获得当前系统日期
	 * 
	 * @author hudaowan Sep 29, 2006 10:43:22 AM
	 * @return
	 */
	public static String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 获得当前系统时间
	 * 
	 * @author hudaowan Sep 29, 2006 11:06:57 AM
	 * @return
	 */
	public static String getTime() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
	
	public static boolean isDateAfter(String timeString, long rating) {
		try {
			Date date = addDay(timeString, rating);
			Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm")
							.format(new Date()));
			long min = date.getTime();
			long nowmin = now.getTime();
			if (nowmin - min > 10 * 60 * 1000)
				return true;
			else
				return false;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 判断传入的日期是否大于当前系统时间额定的时间
	 * 
	 * @author hudaowan Sep 29, 2006 10:47:32 AM
	 * @param timeString
	 * @param rating
	 * @return
	 */
	public static boolean isAfter(String timeString, int rating) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(timeString);
			Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm")
							.format(new Date()));

			long min = date.getTime();
			long nowmin = now.getTime();
			long count = rating * 60 * 1000;
			if (nowmin - min > count)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断传入的日期是否大于当前系统时间额定的时间
	 * 
	 * @author hudaowan Sep 29, 2006 10:56:47 AM
	 * @param timeString
	 * @param rating
	 * @return
	 */
	public static boolean isBefore(String timeString, int rating) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(timeString);
			Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm")
							.format(new Date()));
			long min = date.getTime();
			long nowmin = now.getTime();

			if (nowmin - min < rating * 60 * 1000)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据指定的日期格式显示日期
	 * 
	 * @author hudaowan Sep 29, 2006 10:21:46 AM
	 * @param dDate
	 * @param sFormat
	 * @return
	 */
	public static String formatDate(Date dDate, String sFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		String dateString = formatter.format(dDate);
		return dateString;
	}
	
	public static String dateToString(Date dDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dDate);
		return dateString;
	}

	public static Date toDate(Date dDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dDate);
		return strToDate(dateString, "yyyy-MM-dd");
	}
	
	public static Date toDatetime(Date dDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dDate);
		return strToDate(dateString, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将字符转换为指定的日期格式输出
	 * 
	 * @author hudaowan Sep 29, 2006 10:26:36 AM
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static Date strToDate(String s, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date1;
		try {
			Date theDate = formatter.parse(s);
			Date date = theDate;
			return date;
		} catch (Exception ex) {
			date1 = null;
		}
		return date1;
	}

	/**
	 * 将字符转换为日期
	 * 
	 * @author hudaowan Sep 29, 2006 10:27:45 AM
	 * @param s
	 * @return
	 */
	public static Date strToDate(String s) {
		Date date;
		try {
			DateFormat df = DateFormat.getDateInstance();
			Date theDate = df.parse(s);
			Date date1 = theDate;
			return date1;
		} catch (Exception ex) {
			date = null;
		}
		return date;
	}

	/**
	 * 添加小时和分
	 * 
	 * @author hudaowan 2006-10-27 下午02:38:27
	 * @param sDate
	 * @param iNbDay
	 * @return
	 */
	public static Date addMinute(String sDate, long iNbTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
		cal.add(Calendar.MINUTE, (int) iNbTime);
		Date date = cal.getTime();
		return date;
	}

	public static Date addHour(String sDate, long iNbTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
		cal.add(Calendar.HOUR_OF_DAY, (int) iNbTime);
		Date date = cal.getTime();
		return date;
	}

	/**
	 * 给指定添加天数
	 * 
	 * @author hudaowan Sep 29, 2006 10:11:30 AM
	 * @param dDate
	 * @param iNbDay
	 * @return
	 */
	public static Date addDay(String sDate, long iNbDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
		cal.add(Calendar.DAY_OF_MONTH, (int) iNbDay);
		Date result = cal.getTime();
		return result;
	}
	
	/**
	 * 给但前时间添加天数
	 *@author hudaowan
	 *@date  Nov 3, 2008 12:05:54 PM
	 *@param sDate
	 *@param iNbDay
	 *@return
	 */
	public static Date addDay(long iNbDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, (int) iNbDay);
		Date result = cal.getTime();
		return result;
	}

	/**
	 * 给当前时间添加 周
	 * 
	 * @author hudaowan Sep 29, 2006 10:13:27 AM
	 * @param dDate
	 * @param iNbWeek
	 * @return
	 */
	public static Date addWeek(String sDate, long iNbWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
		cal.add(Calendar.WEEK_OF_YEAR, (int) iNbWeek);
		Date result = cal.getTime();
		return result;
	}

	/**
	 * 给当前时间添加 月
	 * 
	 * @author hudaowan Sep 29, 2006 10:15:29 AM
	 * @param dDate
	 * @param iNbMonth
	 * @return
	 */
	public static Date addMonth(String sDate, int iNbMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
		int month = cal.get(Calendar.MONTH);
		month += iNbMonth;
		int year = month / 12;
		month %= 12;
		cal.set(Calendar.MONTH, month);
		if (year != 0) {
			int oldYear = cal.get(Calendar.YEAR);
			cal.set(Calendar.YEAR, year + oldYear);
		}
		return cal.getTime();
	}

	/**
	 * 给单当前时间添加 年
	 * 
	 * @author hudaowan Sep 29, 2006 10:17:14 AM
	 * @param dDate
	 * @param iNbYear
	 * @return
	 */
	public static Date addYear(Date dDate, int iNbYear) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dDate);
		int oldYear = cal.get(1);
		cal.set(1, iNbYear + oldYear);
		return cal.getTime();
	}

	/**
	 * 得到当前日期是星期几
	 * 
	 * @author hudaowan 2006-10-27 下午01:26:32
	 * @param dDate
	 * @return
	 */
	public static int getWeek(Date dDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dDate);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}
    
	/**
	 * 得到当前天是星期几
	 * 
	 * @author hudaowan
	 * @date Aug 30, 2008 9:48:45 PM
	 * @return
	 */
	public static String getWeeks() {		
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };		
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");		
      // SimpleDateFormat 是一个以与语言环境相关的方式来格式化和分析日期的具体类。它允许进行格式化（日期 -> 文本）、分析（文本
		// -> 日期）和规范化。
		Calendar calendar = Calendar.getInstance();		
		Date date = new Date();		
		try {			
			date = sdfInput.parse(sdfInput.format(date));		
			} catch (ParseException ex) {		
				
			}		
			calendar.setTime(date);		
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);// get 和 set
																// 的字段数字，指示一个星期中的某天。
			System.out.println("dayOfWeek:"+dayOfWeek);		
			return dayNames[dayOfWeek - 1];
	     }
	
	 
	 /** 
	  * 根据一个日期，返回是星期几的字符串 
	  * 
	  * @param sdate 
	  * @return 
	  */
	public static String getWeek(String sdate) { 
		  // 再转换为时间 
		  Date date = strToDate(sdate); 
		  Calendar c = Calendar.getInstance(); 
		  c.setTime(date); 
		  // int hour=c.get(Calendar.DAY_OF_WEEK); 
		  // hour中存的就是星期几了，其范围 1~7 
		  // 1=星期日 7=星期六，其他类推 
		  return new SimpleDateFormat("EEEE").format(c.getTime()); 
		 }
	
	
	/**
	 * 得到但前日期是 那个月的几号
	 * 
	 * @author hudaowan 2006-10-27 下午01:34:37
	 * @param dDate
	 * @return
	 */
	public static int getMonthOfDay(Date dDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dDate);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static void main(String[] gta) {

	}

	/**
	 * 生成随即数
	 * 
	 * @author hudaowan
	 * @date 2008-4-3 上午09:57:56
	 * @param pwd_len
	 * @return
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	public static String genRandNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
	
	/**
	 * 得到当前年
	 * @author liuxd
	 * @date 2011-04-20
	 */
	public static String getNowYear(){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return String.valueOf(year);
	}
	
	/**
	 * 得到当前季度 1 第一季度  2 第二季度 3 第三季度 4 第四季度 
	 * @author liuxd
	 * @date 2011-04-20
	 */ 
    public static int getSeason() {   
        int season = 0;    
        int month = Calendar.getInstance().get(Calendar.MONTH);   
        switch (month) {  
            case Calendar.JANUARY:  
            case Calendar.FEBRUARY:  
            case Calendar.MARCH:  
                season =  1;  
                break;  
            case Calendar.APRIL:  
            case Calendar.MAY:  
            case Calendar.JUNE:  
                season =  2;  
                break;  
            case Calendar.JULY:  
            case Calendar.AUGUST:  
            case Calendar.SEPTEMBER:  
                season =  3;  
                break;  
            case Calendar.OCTOBER:  
            case Calendar.NOVEMBER:  
            case Calendar.DECEMBER:  
                season =  4;  
                break;  
            default:  
                break;  
        }  
        return season;  
    } 
}
