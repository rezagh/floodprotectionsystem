package com.fps.utils;

import java.util.Calendar;

/**
 * 
 * Date and date related util methods
 *
 */
public class TimeUtil {
	
	/**
	 *	For simlicity we only display the hour of the day as time 
	 */
	public static int getTime(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
		
	}
	
	/**
	 *	For simplicity we only use day of the month as date 
	 */
	public static int getDate(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	
}
