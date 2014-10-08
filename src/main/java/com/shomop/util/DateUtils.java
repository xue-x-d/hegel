package com.shomop.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("deprecation")
public abstract class DateUtils {
	public static final long ONE_DAY = 86400000;
	public static final long ONE_HOUR = 3600000;
	public static final long ONE_MONTH = ONE_DAY * 30;
	public static final int MONTH = 1;
	public static final int WEEK = 2;
	public static final int YEAR = 3;
	public static final int HOUR_OF_DAY = 4;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat day = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
	
	public static Date parseDateTime(String str) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(str);
	}

	public static Date parseDate(String str) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.parse(str);
	}
	
	public static Date parseDay(String str) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.parse(str);
	}
	
	public static String formatDay(Date date) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}
	
	public static String formatDay(Date date,String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	public static String formatSimpleDate(Date date) throws ParseException {
		DateFormat df = new SimpleDateFormat("MM-dd");
		return df.format(date);
	}

	public static Date parseCurrentTimeDateHour(String str)throws ParseException {
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date date = df.parse(str);
		return formateCurrentDate(date);
	}
	
	public static Date parseCurrentTimeDateHour(Date date)throws ParseException {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, date.getHours());
		c.set(Calendar.MINUTE, date.getMinutes());
		c.set(Calendar.SECOND, date.getSeconds());
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date parseEndTimeOfCurrentTime(String str)throws ParseException {
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date date = df.parse(str);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, date.getHours());
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	public static List<Date[]> splitTimeByDays(Date start, Date end, int days) {
		return splitTimeByHours(start, end, 24 * days);
	}

	public static List<Date[]> splitTimeByHours(Date start, Date end, int hours) {
		List<Date[]> dl = new ArrayList<Date[]>();
		while (start.compareTo(end) < 0) {
			Date _end = addHours(start, hours);
			if (_end.compareTo(end) > 0) {
				_end = end;
			}
			Date[] dates = new Date[] { (Date) start.clone(),(Date) _end.clone() };
			dl.add(dates);
			start = _end;
		}
		return dl;
	}

    /**
     * 获得这个月的开始时间 例如2012年1月 :2012-01-01 00:00:00
     * @return
     */
    public  static  Date getMonthStartTime(int year,int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.DATE, 1);
        return DateUtils.getStartTime(cal.getTime());
    }

    /**
     * 获得这个月的结束，例如2012年1月 :2012-01-31 23:59:59
     * @return
     */
    public  static  Date getMonthEndTime(int year,int month) {
        Calendar cal = Calendar.getInstance();
        Date now = null;
        try {
            cal.set(Calendar.DATE, 1);
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month);
            cal.add(Calendar.DATE, -1);
            now =cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DateUtils.getEndTime(now);
    }
    
    /**
     * 返回指定日期是星期几,星期一是2,星期天是1
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 返回指定日期是这个月的第几周
     * @param date
     * @return
     */
    public static int getWeekNumOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);    
    }
    /**
     * 获得这个星期的开始时间 例如2012年1月 :2012-01-01 00:00:00
     * @return
     */
    public  static  Date getWeekStartTime(int year,int month,int week) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.WEEK_OF_MONTH,week);
        int dw=cal.get(Calendar.DAY_OF_WEEK);
        cal.setTimeInMillis(cal.getTimeInMillis()-(dw-2)*24*60*60*1000);
        return DateUtils.getStartTime(cal.getTime());
    }

    /**
     * 获得这个星期的结束，例如2012年1月 :2012-01-31 23:59:59
     * @return
     */
    public  static  Date getWeekEndTime(int year,int month,int week) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        Date now = null;
        try {
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month-1);
            cal.set(Calendar.WEEK_OF_MONTH,week);
            int dw=cal.get(Calendar.DAY_OF_WEEK);
            cal.setTimeInMillis(cal.getTimeInMillis()+(8-dw)*24*60*60*1000);
            now =cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DateUtils.getEndTime(now);
    }
    
    
	public static Date addMinutes(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, amount);
		return c.getTime();
	}

	public static Date addHours(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, amount);
		return c.getTime();
	}

	public static Date addDays(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);
		return c.getTime();
	}

	public static Date addMonths(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, amount);
		return c.getTime();
	}

	/**
	 * 获取今天的开始时刻。
	 */
	public static Date getTodayStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取date的开始时刻
	 */
	public static Date getStartTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * 传入时间的当天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndTime(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();

	}

	/**
	 * 获取两个日期之间相差的月份
	 * @return
	 */
	public static int monthOfTwoDate(Date fDate, Date nDate) {
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(fDate);
		int sYear = starCal.get(Calendar.YEAR);
		int sMonth = starCal.get(Calendar.MONTH);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(nDate);
		int eYear = endCal.get(Calendar.YEAR);
		int eMonth = endCal.get(Calendar.MONTH);
		return ((eYear - sYear) * 12 + (eMonth - sMonth));
	}

	/**
	 * 获取两个日期之间相差的星期数
	 * 
	 * @return
	 */
	public static int computeWeek(Date startDate, Date endDate) {
		int weeks = 0;
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		while (beginCalendar.before(endCalendar)) {
			// 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环
			if (beginCalendar.get(Calendar.YEAR) == endCalendar
					.get(Calendar.YEAR)
					&& beginCalendar.get(Calendar.MONTH) == endCalendar
							.get(Calendar.MONTH)
					&& beginCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == endCalendar
							.get(Calendar.DAY_OF_WEEK_IN_MONTH)) {
				break;
			} else {
				beginCalendar.add(Calendar.DAY_OF_YEAR, 7);
				weeks += 1;
			}
		}
		return weeks;
	}

	/**
	 * 获取当前时间前month个月的第一天开始时间 例如当前为7月1号,month=1,则为6月1号
	 * 
	 * @param month
	 * @return
	 */
	public static Date getStartDayMonth(int month, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date date = null;
		if (type == MONTH) {
			// 一号
			cal.set(Calendar.DAY_OF_MONTH, 1);
			date = cal.getTime();
			date = DateUtils.addMonths(date, -month);
		} else {
			// 星期一
			cal.set(Calendar.DAY_OF_WEEK, 2);
			date = cal.getTime();
			date = DateUtils.addDays(date, -month * 7);
		}
		return date;
	}

	/**
	 * 获取当前时间前month个月的最后一天结束时间.
	 * 
	 * @param month
	 * @return
	 */
	public static Date getEndDayMonth(int month, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		Date date = null;
		if (type == MONTH) {
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
			date = cal.getTime();
			date = DateUtils.addMonths(date, -month);
		} else {
			// 星期天
			cal.set(Calendar.DAY_OF_WEEK, 7);
			date = cal.getTime();
			date = DateUtils.addDays(date, 1);
			date = DateUtils.addDays(date, -month * 7);
		}
		return date;
	}

	/**
	 * 
	 * 获取两个日期之间相差几天 如果间隔小于24小时为0 nDate - fDate
	 * 
	 * @param fDate
	 * @param nDate
	 * @return
	 */
	public static int daysOfTwoDate(Date fDate, Date nDate) {
		if (null == fDate || null == nDate) {
			return -1;
		}
		long intervalMilli = nDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / ONE_DAY);
	}

	public static String getDateString(Date date) {
		return dateFormat.format(date);
	}

	public static String getDayString(Date date) {
		return day.format(date);
	}

	public static String formatYear(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy");
		return df.format(date);
	}

	public static String formatMonth(Date date) {
		DateFormat df = new SimpleDateFormat("MM");
		return df.format(date);
	}

	public static int getDateType(Date date, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(date);
		int dateInt = -1;
		switch (type) {
		case YEAR:
			dateInt = cal.get(Calendar.YEAR);
			break;
		case WEEK:
			dateInt = cal.get(Calendar.WEEK_OF_YEAR);
			break;
		case MONTH:
			dateInt = cal.get(Calendar.MONTH);
			break;
		case HOUR_OF_DAY:
			dateInt = cal.get(Calendar.HOUR_OF_DAY);
			break;
		default:
			break;
		}
		return dateInt;
	}

	public static int getMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int minute = cal.get(Calendar.MINUTE);
		return minute;
	}

	/**
	 * 两个日期的时间比较 忽略日期
	 * @param fDate
	 * @param oDate
	 * @return 0 相等 负数 小于 正数 大于
	 */
	public static int compareTwoTime(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {

			throw new NullPointerException("arguments should not be null.");
		}
		// long f = fDate.getTime();
		// long o = oDate.getTime();
		// // 如果年月日相等有可能跨天也是间隔小于1天的毫秒
		// if(Math.abs(f - o) <= ONE_DAY){
		//
		// return (int)(f - o);
		// }
		// 格式为年月日相等的日期（时间不同）
		fDate = formateCurrentDate(fDate);
		oDate = formateCurrentDate(oDate);
		return (int) (fDate.getTime() - oDate.getTime());
	}

	/**
	 * 复制传入Date的时间 保留当前时间的日期
	 * 
	 * @param date
	 * @return
	 */
	private static Date formateCurrentDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, date.getHours());
		c.set(Calendar.MINUTE, date.getMinutes());
		c.set(Calendar.SECOND, date.getSeconds());
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 拼接日期
	 * @param hourPart
	 * @param datePart
	 * @return
	 */
	public static Date joinDate(Date hourPart, Date datePart) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, datePart.getYear() + 1900);
		c.set(Calendar.MONTH, datePart.getMonth());
		c.set(Calendar.DAY_OF_MONTH, datePart.getDate());
		c.set(Calendar.HOUR_OF_DAY, hourPart.getHours());
		c.set(Calendar.MINUTE, hourPart.getMinutes());
		c.set(Calendar.SECOND, hourPart.getSeconds());
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	public static DateFormat getDateFormat() {
		return dateFormat;
	}

	public static void setDateFormat(DateFormat dateFormat) {
		DateUtils.dateFormat = dateFormat;
	}

	/**
	 * 判断间隔多少天
	 * 如果间隔时间不到1小时 
	 * 算一天
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public static int countDays(Date sDate, Date eDate) {
		int betweenDays = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(sDate);
		c2.setTime(eDate);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1.setTime(eDate);
			c2.setTime(sDate);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		// 闰年平年会有一天误差
//		betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
//		for (int i = 0; i < betweenYears; i++) {
//			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
//			betweenDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
//		}
		if (betweenYears > 0) {
			betweenDays = c1.getActualMaximum(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
			for (int i = 1; i < betweenYears; i++) {
				c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
				betweenDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
			}
			if(betweenYears == 1){
			   betweenDays += c2.get(Calendar.DAY_OF_YEAR);
			}else{
			   betweenDays += c2.getActualMaximum(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);
			}
		}else{
			betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);	
		}
		// 没有判断小时
		if (c2.get(Calendar.HOUR_OF_DAY) > c1.get(Calendar.HOUR_OF_DAY)){
			betweenDays++;
		}
		return betweenDays;
	}

	/**
	 * 将当前日期设置为指定时间
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date formatDateTime(int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 拆分日期为年、月、日
	 * @param date
	 * @return
	 */
	public static int[] splitDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int[] result = { cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH) };
		return result;
	}
	
	/**
	 * 检查日期
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean checkDate(Date start,Date end){
		if(start == null || end == null){
			return false;
		}
		if(end.compareTo(start) <= 0){
			return false;
		}
		return true;
	}
	/**
	 * 同一天
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static boolean areSameDay(Date dateA,Date dateB) {
		Calendar calA = Calendar.getInstance();
		calA.setTime(dateA);
		Calendar calB = Calendar.getInstance();
		calB.setTime(dateB);
		return calA.get(Calendar.YEAR) == calB.get(Calendar.YEAR)
				&& calA.get(Calendar.MONTH) == calB.get(Calendar.MONTH)
				&& calA.get(Calendar.DAY_OF_MONTH) == calB.get(Calendar.DAY_OF_MONTH);
	}

	public static int dayNum(Date date){
	    Date startDate = DateUtils.getStartTime(date);
	    return (int)(DateUtils.addHours(startDate, 8).getTime()/ONE_DAY);
	}
	
	/**
	 * 计算传入的毫转换成天数（四舍五入）
	 * @param totalMillis 可以为负数
	 * @return
	 */
	public static int millisToDays(long totalMillis) {
		int day = Math.round(totalMillis / DateUtils.ONE_DAY);
		// 不足一天的毫秒数
		totalMillis = totalMillis % DateUtils.ONE_DAY;
		// 不足一天大于半天按一天算
		if (Math.abs(totalMillis) > DateUtils.ONE_DAY >> 1) {
			if (totalMillis < 0) {
				day -= 1;
			} else {
				day += 1;
			}
		}
		return day;
	}
	public static void main(String[] args) {

		// /*Date s = DateUtils.addDays(new Date(), -1);
		// System.out.println("taskendtime: "+s.toLocaleString());
		// Date first = DateUtils.getStartTime(DateUtils.addDays(s,1));
		// System.out.println("firstTime: "+first.toLocaleString());
		// Date t = new Date();
		// // t = DateUtils.addDays(t, 1);
		// // t = DateUtils.addMinutes(t, -36);
		// System.out.println("today: "+t.toLocaleString());
		// int days = daysOfTwoDate(first, t);
		// System.out.println("internal: "+days);
		// for (int i = 0; i <= days; i++) {
		// System.out.println("-----------"+(i+1)+"-----------");
		// // 下载当天新增订单
		// Date start = DateUtils.addDays(first, (i));
		// System.out.println("start: "+start.toLocaleString());
		// Date end = DateUtils.addDays(start,1);// 截止时间
		// // 最后一天
		// if(i == days){
		// end = new Date();
		// }
		// System.out.println("end: "+end.toLocaleString());
		// }
		//
		// Date a = DateUtils.addDays(new Date(), -1);
		// System.out.println(a.compareTo(new Date()));
		//
		// System.out.println(DateUtils.getEndTime(new
		// Date()).toLocaleString());
		//
		// Date t1 = new Date();
		// System.out.println(DateUtils.daysOfTwoDate(DateUtils.addHours(t1,
		// -10),t1));*/
		//
		// String dateStr = "19:20";
		// try {
		// Date date = DateUtils.parseDateHour(dateStr);
		// System.out.println(date.toLocaleString());
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// Date t = new Date();
		// // Date f1 = DateUtils.addDays(t,-1);
		// Date f1 = DateUtils.addHours(t,-24);
		// // System.out.println(DateUtils.compareTwoTime(f1,t) >= 0);
		//
		// System.out.println(DateUtils.daysOfTwoDate(f1,t));
		// System.out.println(getDateString(getStartDayMonth(1,WEEK)));
		// Date date = getEndDayMonth(1,WEEK);
		// Calendar c = Calendar.getInstance();
		// c.setTime(date);
		// System.out.println(c.get(Calendar.DAY_OF_WEEK));
		// System.out.println(getDateString(getEndDayMonth(1,WEEK)));
		// System.out.println(getDateString(getStartDayMonth(1,MONTH)));
		// System.out.println(getDateString(getEndDayMonth(1,MONTH)));
		// Date date1 = new Date();date1.getTime();
		// Date date2 = DateUtils.addDays(new Date(), -36);
		// date2 = DateUtils.addHours(date2,-2);
		// System.out.println(monthOfTwoDate(date1,date2));
		// System.out.println(DateUtils.countDays(date1,date2));
		// long a = (date1.getTime()-date2.getTime())/(24*60*60*1000);
		// System.out.println(a);
		//
		// Calendar c1 = Calendar.getInstance();
		// c1.add(Calendar.YEAR,1);
		// System.out.println(c1.getActualMaximum(Calendar.DAY_OF_YEAR));

		// Date datec = new Date();
		// Date dateb = DateUtils.addDays(datec,2);
		// System.out.println(daysOfTwoDate(dateb,datec));
		// System.out.println(countDays(dateb,datec));
		// Date start = DateUtils.addDays(datec,-2);
		// System.out.println(start.getTime());
		// System.out.println(DateUtils.addMonths(start,-1).getTime());
		// System.out.println(DateUtils.formatDateTime(11,0,0).toLocaleString());
		// System.out.println("---------------------");
		// System.out.println(datec.getTime());
		// System.out.println(DateUtils.addMonths(datec,-3).getTime());
		// int[]a = splitDate(DateUtils.addMonths(datec,-3));
		// for (int i = 0; i < a.length; i++) {
		// System.out.println(a[i]);
		// }
		// System.out.println(getDateType(datec,DateUtils.MONTH));
		try {
			Date date = parseDate("2014-05-05 02:00");
			Date date2 = parseDate("2000-03-01 00:00");
			System.out.println(DateUtils.countDays(date,date2));
			System.out.println(getDateString(date));
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			System.out.println(c.getActualMaximum(Calendar.DAY_OF_YEAR));
			System.out.println(c.get(Calendar.DAY_OF_YEAR));
			System.out.println(getDateType(new Date(),1));
			System.out.println(getDateType(new Date(),2));
			System.out.println(getDateType(new Date(),3));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("-------------------------");
		try {
			Date date = null;
			for(int i=0;i<5;i++){
				date = parseDate("2014-05-05 02:00");
				date = DateUtils.addDays(date,i);
				System.out.println(DateUtils.getDateString(date)+"  "+date.getTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(new Date().getTime());
		System.out.println(DateUtils.addMonths(new Date(),-3).getTime());
		Calendar cal2 = Calendar.getInstance();
		System.out.println(cal2.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		System.out.println(cal2.get(Calendar.MONTH));
		System.out.println("-------------------------");
		try {
			Date yesterday = parseDate("2014-08-31 01:00:00");
			int week = getWeekNumOfMonth(yesterday);
			System.out.println("week: " + week);
			Calendar cal = Calendar.getInstance();
			cal.setTime(yesterday);
			cal.set(Calendar.YEAR, 2014);
			cal.set(Calendar.MONTH, 7);
			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH,week);
			cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
			// 结束时间
			System.out.println(DateUtils.getDateString(DateUtils.getEndTime(cal.getTime())));
			// 8月最后一个周只有周日，9月第一个周的星期一是9-08
			// 即就是存在一些星期既不是上个月最后一周的额时间，也不是下个月第一周的时间
			// 好扯蛋。。。
//			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH,-1);
//			cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			// 起始时间 直接这周的结束时间减掉6天后取开始时间
			System.out.println(DateUtils.getDateString(DateUtils.getStartTime(DateUtils.addDays(cal.getTime(),-6))));
			System.out.println("-------------------------");
			System.out.println(DateUtils.formatDay(new Date(),"M月dd日 HH点"));
			System.out.println(DateUtils.class.getName());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
