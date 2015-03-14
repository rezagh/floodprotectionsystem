package com.fps.test;

import org.junit.Assert;
import org.junit.Test;

import com.fps.idl.LMS.Alarm;
import com.fps.idl.LMS.LMS;
import com.fps.idl.Sensors.Sensor;

public class LmsTester extends TestBase{

	@Test
	public void raiseAlarmInLMS(){
		String lmsName ="lms_1";
		String sensorName="sensor_a1";
		String zoneName = "zone_a";

		LMS lms = getLMS(lmsName);
		
		lms.raise_Alarm(createAlarm(sensorName, zoneName));
		
	}
	
	@Test
	public void addSensorToLMS(){
		String lmsName ="lms_1";
		String sensorName="sensor_a1";
		String zoneName = "zone_a";
		
		LMS lms = getLMS(lmsName);
		Sensor sensor = getSensor(sensorName);
		System.out.println("adding sensor: " + sensor.name() + " to zone: " + zoneName);
		lms.add_Sensor(sensor,zoneName );
	}

	@Test
	public void getCurrentAlamStat(){
		String lmsName ="lms_1";
		String sensorName="sensor_a1";
		String zoneName = "zone_a";

		
		System.out.println("logs for " + lmsName);
		LMS lms = getLMS(lmsName);
		
		lms.raise_Alarm(createAlarm(sensorName, zoneName));
		Alarm alarm = lms.get_currrent_alarm_status(zoneName);
		Assert.assertNotNull(alarm);
	}
	
	@Test
	public void getLogs(){
		String lmsName ="lms_1";
		String sensorName="sensor_a1";
		String zoneName = "zone_a";

		
		System.out.println("logs for " + lmsName);
		LMS lms = getLMS(lmsName);
		
		lms.raise_Alarm(createAlarm(sensorName, zoneName));
		Alarm[] alarms = lms.log();
		Assert.assertNotNull(alarms);
		Assert.assertTrue(alarms.length > 0);
		
		for(Alarm alarm: alarms){
			System.out.println(alarm.date + " " + alarm.time + " " + alarm.sensor + " " + alarm.zone);
		}
	}
}