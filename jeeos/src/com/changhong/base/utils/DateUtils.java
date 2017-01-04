package com.changhong.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 日期工具类
 * 
 * @author wanghao
 */
public class DateUtils {
	
	//public final static String LONG_DATE_FORMAT="yyyy-MM-dd HH:mm:ss:SSS";
	
	public final static String DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	public final static String DATE_FORMAT="yyyy-MM-dd";

	private String dateString;	
	
	public DateUtils(){
		dateString = new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
	}
	/**
	 * 根据指定格式，解释字符串，生成日期对象
	 * @param src　日期字符串
	 * @param format　格式化字符串
	 * @return　不能转化时返回Null
	 */		
	public  static Date parseDate(String src,String format){
		Date date=null;
		if(src==null || src.equals(""))
			return null;
		try{
			date=new SimpleDateFormat(format).parse(src);
		}catch(Exception e){
			return null;
		}
		return date;
	}

	/**
	 * 把传入的日期，按指定格式返回其字符串形式
	 * @param d　日期对象
	 * @param format　格式字符串
	 * @return　不能转换返回Null
	 */
	public static String dateToString(Date d,String format){
		String str = null;
		if(d==null){
			return null;
		}
		else{
			try {
				str = new SimpleDateFormat(format).format(d);
			} catch (Exception e) {
				return null;
			}
		}
			return str;
	}

	/**
	 * 获取当前时间yyyy-MM-dd HH:mm:ss字符串形式 
	 * @return
	 */
	public  static String getCurrentDate() {
		return getCurrentDate(DATE_TIME_FORMAT);
	}
	/**
	 * 获取月天MMdd字符串形式0703 
	 * @return
	 * @throws ParseException 
	 */
	public  static String getMonthDay() throws ParseException {
		return getCurrentDate("MMdd");
	}

	/**
	 * 获取当前时间指定格式的字符串形式
	 * 格式错误按yyyy-MM-dd HH:mm:ss
	 * @param String sFormat　日期格式字符串
	 * @return String　转换成指定格式的字符串形式
	 */	
	public  static String getCurrentDate(String sFormat) {
		if (sFormat == null || sFormat.equals(""))
			sFormat = DATE_TIME_FORMAT;
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		return formatter.format(new java.util.Date());
	}	
	
	/**
	 * 计算时间差：天 /小时/分/秒
	 * @param beginTime  开始日期
	 * @param endTime    截止日期
	 * @param type       时间差类型：1天，2小时，3分，4秒
	 * @return
	 * @throws ParseException
	 */
    public static String getTimeLength(String beginTime,String endTime,String type) throws ParseException{
    	
    	String timeLength = "";
    	Date begin = parseDate(beginTime, DATE_TIME_FORMAT);
    	Date end = parseDate(endTime, DATE_TIME_FORMAT);
        long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
        if("1".equals(type)){
        	 long day = between/(24*3600);
        	 timeLength = day+"";
        }else if("2".equals(type)){
        	 long hour = between%(24*3600)/3600;
	    	 timeLength = hour+"";
	    }else if("3".equals(type)){
	    	 long minute = between%3600/60;;
			 timeLength = minute+"";
		}else if("4".equals(type)){
			 long second = between%60;
			 timeLength = second+"";
		}
        return timeLength;
    }
    
    /**
     * 获取当前年月
     * @return curmonth 格式"yyyy-MM"
     */
	public static String getCurmonth(){
    	java.util.Date date = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String curmonth = formatter.format(date);
        return curmonth;
    }

	/**
	 * 获取上一月
	 * @return lastmonth  格式"yyyy-MM"
	 */
	public static String getLastmonth(){
    	Calendar c = Calendar.getInstance();
        java.text.SimpleDateFormat format =  new java.text.SimpleDateFormat("yyyy-MM");
        c.add(Calendar.MONTH, -1);
        String lastmonth = format.format(c.getTime());
        return lastmonth;
    }
	
	/**
	 * 获取上一天日期 yyyy-MM-dd字符串形式 
	 * @return
	 * @throws ParseException 
	 */
	public  static String getLastDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = format.parse(date);
		Calendar c = Calendar.getInstance();
		c.setTime(d_date);
		c.add(Calendar.DATE, -1);
		date = format.format(c.getTime());
		return date;
	}
	

	
	/**
	 * 获取上一月
	 * @return lastmonth  格式"yyyy-MM-dd"
	 */
	public static String getLastMonthDay(){
		Calendar c = Calendar.getInstance();
		java.text.SimpleDateFormat format =  new java.text.SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.MONTH, -1);
		String date = format.format(c.getTime());
		return date;
	}
	/**
	 * 获取10天前日期
	 * @return lastmonth  格式"yyyy-MM-dd"
	 */
	public static String getLastTenDay(){
		Calendar c = Calendar.getInstance();
		java.text.SimpleDateFormat format =  new java.text.SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DATE, -9);
		String date = format.format(c.getTime());
		return date;
	}
	
	/**
	 * 根据日期获取 年周   如：2014-01-07  =》2014年第2周
	 * @param date
	 * @return *年第*周     2014年第2周   
	 * @throws ParseException
	 */
	public static String getWeekOfYear(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = format.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(d_date);
		String weekOfYear = calendar.get(Calendar.YEAR)+"年第"+calendar.get(Calendar.WEEK_OF_YEAR)+"周";
		return weekOfYear;
	}
	
	/**
	 * 根据日期获取 星期天 如：2014-05-05  =》2014-05-11
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getSunday(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = df.parse(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(d_date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 获取本周一的日期
		String str_date = df.format(cal.getTime());
		return str_date;
	}
	
	/**
	 * 根据日期获取 星期一 如：2014-04-16  =》2014-04-14
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMonday(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = df.parse(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(d_date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		String str_date = df.format(cal.getTime());
		return str_date;
	}
	
	/**
	 * 根据日期获取上一个 星期天 如：2014-04-09  =》2014-03-31
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastSunday(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = df.parse(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(d_date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 获取本周一的日期
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		String str_date = df.format(cal.getTime());
		return str_date;
	}
	/**
	 * 根据日期获取上一个 星期一 如：2014-04-09  =》2014-03-31
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMonday(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = df.parse(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(d_date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
	    cal.add(Calendar.WEEK_OF_YEAR, -1);
		String str_date = df.format(cal.getTime());
		return str_date;
	}
	
	/**
	 * 根据日期获取下一个 星期天 如：2014-04-09  =》2014-04-20
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getNextSunday(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = df.parse(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(d_date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 获取本周一的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		String str_date = df.format(cal.getTime());
		return str_date;
	}
	/**
	 * 根据日期获取下一个星期一 如：2014-04-09  =》2014-04-14
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getNextMonday(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = df.parse(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(d_date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
	    cal.add(Calendar.WEEK_OF_YEAR, 1);
		String str_date = df.format(cal.getTime());
		return str_date;
	}
	
	/**
	 * 判断是否相距超过10周   isOverDistanceWeek("2014-02-23","2014-04-27",10)= false;isOverDistanceWeek("2014-02-16","2014-04-27",10)= true;
	 * true 表示超过count周     ；false 表示没有超过
	 * @param beginDate
	 * @param endDate
	 * @param count
	 * @return
	 * @throws ParseException
	 */
	public static Boolean isOverDistanceWeek(String beginDate, String endDate, Integer count) throws ParseException{
		boolean isOver = true;
		String date = getSunday(endDate);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = df.parse(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(d_date);
		cal.add(Calendar.WEEK_OF_YEAR, -count);
        Date d_endDateMonday = cal.getTime();
        Date d_beginDateMonday = df.parse(getSunday(beginDate));
		if(d_endDateMonday.before(d_beginDateMonday)){
			return isOver = false;
		}
		return isOver;
	}
    
	/**
	 * 返回年份数值
	 * @return
	 */
	public int getYear() {
		return Integer.parseInt(this.dateString.substring(0, 4));
	}
	/**
	 * 返回4位年份字符串
	 * @return
	 */
	public String getStrYear() {
		return this.dateString.substring(0, 4);
	}

	/**
	 * 返回月份数值
	 * @return int
	 */
	public int getMonth() {
		return Integer.parseInt(this.dateString.substring(5,7));
	}
	
	/**
	 * 返回2位月份字符串
	 * @return string
	 */
	public String getStrMonth() {
		return this.dateString.substring(5,7);
	}

	/**
	 * 返回日数值
	 * @return int
	 */
	public int getDay() {
		return Integer.parseInt(this.dateString.substring(8,10));
	}
	/**
	 * 返回2位日字符串
	 * @return String
	 */
	public String getStrDay() {
		return this.dateString.substring(8,10);
	}
	
	/**
	 * 返回小时值
	 * @return int
	 */
	public int getHour() {
		return Integer.parseInt(this.dateString.substring(11,13));
	}

	/**
	 * 返回2位小时字符串
	 * @return String
	 */
	public String getStrHour() {
		return this.dateString.substring(11,13);
	}

	/**
	 * 返回分钟值
	 * @return int
	 */
	public int getMinute() {
		return Integer.parseInt(this.dateString.substring(10, 12));
	}

	/**
 	 * 返回2位分钟字符串
	 * @return String
	 */
	public String getStrMinute() {
		return this.dateString.substring(14,16);
	}

	/**
	 * 返回秒值
	 * @return int
	 */
	public int getSecond() {
		return Integer.parseInt(this.dateString.substring(14,16));
	}
	/**
 	 * 返回2位秒字符串
	 * @return String
	 */
	public String getStrSecond() {
		return this.dateString.substring(17,19);
	}

	/**
	 * 返回年月日字符串格式如　1997-07-01
	 * @return Sting
	 */
	public String getStrDate() {
		return this.dateString.substring(0,10);
	}
	
	/**
	 * 返回时分秒字符串　01:01:01
	 * @return Sting
	 */
	public String getStrTime() {
		return this.dateString.substring(12,19);
	}	
	public static String inTenDayBeginDate(String begindate,String enddate){
		try{
		 SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		 Calendar   rightNow   =   Calendar.getInstance(); 
		 Date date1=formatter.parse(begindate);    
	     Date date2=formatter.parse(enddate);
	     long l = date2.getTime() - date1.getTime();
	     long d = l/(24*60*60*1000);
	     if(d>10)
	     {
			 rightNow.setTime(date2); 
			 rightNow.add(Calendar.DATE,-9);
			 
	    	 begindate=formatter.format(rightNow.getTime());
	    	 
	     }
		return begindate;
		}catch(Exception e){
			return begindate;
		}
	}
	public static String getWeekOfYearInt(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d_date = format.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(d_date); 
		String weekOfYear = (String.valueOf(calendar.get(Calendar.YEAR))+String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)));
		return weekOfYear;
	}
    /**
     * 获取当前年月
     * @return curmonth 格式"yyyyM"
     */
	public static String getCurmonth1(){
    	java.util.Date date = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyM");
        String curmonth = formatter.format(date);
        return curmonth;
    }
	
	/**
	 * 获取上一天日期 yyyy-MM-dd字符串形式 
	 * @return
	 * @throws ParseException 
	 */
	public  static String getYesterdayDate() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		String strdate = format.format(c.getTime());
		return strdate;
	}
	
	 public static void main(String[] args) throws ParseException{
		 String date1 = getCurrentDate();
		 System.out.println(date1);
		 String date2 = "2015-05-24 23:49:23";
		 System.out.println(date2);
		 String timeLength = getTimeLength(date1, date2, "1");
		 String timeLength2 = getTimeLength(date1, date2, "2");
		 String timeLength3 = getTimeLength(date1, date2, "3");
		 String timeLength4 = getTimeLength(date1, date2, "4");
		 System.out.println(timeLength+"天"+timeLength2+"小时"+timeLength3+"分"+timeLength4+"秒");
	 }
}
