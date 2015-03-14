package com.fps.utils;

import java.util.Calendar;

public class TimeUtil {
	public static int getTime(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
		
	}
	
	public static int getDate(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	
}
