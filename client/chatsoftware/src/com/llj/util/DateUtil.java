package com.llj.util;

/**
 * 日期处理工具类
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date) {
//		return format(date, "yyyy-MM-dd HH:mm:ss");
		return format(date, "yyyy-MM-dd"); //modified with wangxz im 2014-5-4
	}
	
	public static String format2(Date date) {
//		return format(date, "yyyy-MM-dd HH:mm:ss");
		return format(date, "yyyy-MM-dd HH:mm:ss"); //modified with wangxz im 2014-5-4
	}
	
	public static String format3(Date date) {
//		return format(date, "yyyy-MM-dd HH:mm:ss");
		return format(date, "HH:mm:ss"); //modified with wangxz im 2014-5-4
	}
	
	public static String format4(Date date) {
//		return format(date, "yyyy-MM-dd HH:mm:ss");
		return format(date, "yyyyMMddHHmmss"); //modified with wangxz im 2014-5-4
	}
	
	/**
	 * 
	 *  @Enclosing_Method  : calculateDate
	 *  @Written by        : Hong Liang
	 *  @Creation Date     : 2015年8月3日 下午3:10:44 
	 *  @version           : v1.00
	 *  @Description       :  传入1，则将当前日期加1天，传入-1则将当前日期减一天。
	 *  
	 *  @param calculate_days 计算的天数，减
	 *  @return
	 * @throws ParseException 
	 *
	 */
	public static String calculateDate(int calculate_days) throws ParseException{
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 String now_date = format(new Date(),"yyyy-MM-dd");
		 Date d = format.parse(now_date);
		 Calendar c = Calendar.getInstance();  
         c.setTime(d);  
         c.add(c.DATE, calculate_days);  
         Date temp_date = c.getTime();   
         return format.format(temp_date);
	}
	
	/**
	 *  @Enclosing_Method  : calculateDate
	 *  @Written by        : Hong Liang
	 *  @Creation Date     : 2016年8月8日 下午4:34:42 
	 *  @version           : v1.00
	 *  @Description       :  根据传入的日期与计算天数做加减天数
	 *  
	 *  @param date
	 *  @param calculate_days
	 *  @return
	 *  @throws ParseException
	 **/
	public static String calculateDate(String date, int calculate_days) throws ParseException{
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 Date d = format.parse(date);
		 Calendar c = Calendar.getInstance();  
        c.setTime(d);  
        c.add(c.DATE, calculate_days);  
        Date temp_date = c.getTime();   
        return format.format(temp_date);
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @param pattern
	 *            字符串格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
//			pattern = "yyyy-MM-dd HH:mm:ss";
			pattern = "yyyy-MM-dd "; //modified with wangxz im 2014-5-4
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @return 日期类型
	 */
	public static Date format(String date) {
		return format(date, null);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @param pattern
	 *            格式
	 * @return 日期类型
	 */
	public static Date format(String date, String pattern) {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
//			pattern = "yyyy-MM-dd HH:mm:ss";
			pattern = "yyyy-MM-dd"; //modified with wangxz im 2014-5-4
		}
		if (date == null || date.equals("") || date.equals("null")) {
			return new Date();
		}
		Date d = null;
		try {
			d = new java.text.SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return d;
	}
	
	//日期加减分钟，返回运算过后的日期
	public static String addMinutes(String date,int addAmount) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt=sdf.parse(date);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MINUTE,addAmount);//分钟加减
		Date dt1=rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}
	
}