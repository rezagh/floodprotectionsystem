package com.fps.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fps.idl.LMS.Alarm;
import com.fps.idl.Sensors.Reading;
import com.fps.idl.Sensors.WarningLevel;
import com.fps.rmc.domain.UserDo;

public class FileUtil {

	public static void writeSensorReadingLog(Reading reading, String logFile){

		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
		    out.println(reading.date + "," + reading.time + "," + reading.reading);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeLmsAlarmLog(Alarm alarm, String logFile){

		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
		    out.println(alarm.date + "," + alarm.time + "," + alarm.sensor + "," + alarm.zone);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readFromLog(String logfile){

		try {
			for (String line : Files.readAllLines(Paths.get(logfile),Charset.defaultCharset())) {
			    System.out.println(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeUserInfo(UserDo user, String userLog) {
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(userLog, true)));
		    out.println(user.getName() + "," + user.getEmail() + "," + user.getZone() + "," + user.getType().name());
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void writeSensorAlarmLog(WarningLevel level, String alarmsLogFile) {
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(alarmsLogFile, true)));
		    out.println(TimeUtil.getDate()+","+TimeUtil.getTime()+","+level.value());
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
