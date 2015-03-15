package com.fps.lms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.ORB;

import com.fps.clients.RmcClient;
import com.fps.clients.SensorsClient;
import com.fps.idl.LMS.Alarm;
import com.fps.idl.LMS.LMSPOA;
import com.fps.idl.RMC.AlarmData;
import com.fps.idl.Sensors.Sensor;
import com.fps.idl.Sensors.WarningLevel;
import com.fps.utils.FileUtil;

class LmsImpl extends LMSPOA {
	private ORB orb;
	private String name;
	private String rmcName;
	
	private static String logFile;
	
	/**
	 * Keeping a sensor zone coverage
	 */
	Map<String,String> sensors = new HashMap<String,String>();//sensor:zone
	List<Alarm> allAlarms = new ArrayList<Alarm>();
	Map<String , Alarm> confirmedAlarms = new HashMap<String,Alarm>();//zone:alarm
	
	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	@Override
	public String name() {
		return this.name;
	}

	/**
	 * retrieves the log of sensor alerts
	 */
	@Override
	public Alarm[] log() {
		Alarm[] array = new Alarm[allAlarms.size()];
		return allAlarms.toArray(array);
	}


	/**
	 * Sensors call this when they raise alarm
	 */
	@Override
	public void raise_Alarm(Alarm alarm) {
		//set the alarm zone.
		for(String sensor : sensors.keySet()){
			if (alarm.sensor.equals(sensor)){
				alarm.zone = sensors.get(sensor);
			}
		}
		
		System.out.println("alarm raised by sensor:" + alarm.sensor);
		FileUtil.writeLmsAlarmLog(alarm, logFile);
		allAlarms.add(alarm);
		
		//check with pair
		WarningLevel fromPair = checkWithPair(alarm.sensor);
		
		//only raise alarm to RMC if pair = high
		if (fromPair != null && fromPair == WarningLevel.HIGH){
			System.out.println("pair confirmed high!");
			raiseAlarmToRMC(alarm,getPair(alarm.sensor) );
			confirmedAlarms.put(alarm.zone,alarm);
		}else{
			System.out.println("pair didn't confirm!");
		}
	}
	
	private String getPair(String sensorName){
		SensorsClient client = new SensorsClient(sensorName);
		return client.getPairName();
	}
	
	private WarningLevel checkWithPair(String sensorName) {
		String pairName = getPair(sensorName);
		SensorsClient pairSensor = new SensorsClient(pairName);
		WarningLevel level = pairSensor.getWarningLevel();
		return level;
	}

	/**
	 * Raise an alarm to RMC
	 */
	public void raiseAlarmToRMC(Alarm alarm, String pair){
		System.out.println("Raising alarm to RMC");
		RmcClient rmc = new RmcClient(this.getRmcName());
		AlarmData alarmData = new AlarmData();
		alarmData.aConfirmingSensor = pair;
		alarmData.aReading = alarm;
		rmc.raiseAlarm(alarmData);
	}

	@Override
	public Alarm get_currrent_alarm_status(String zone) {
		Alarm alarm = confirmedAlarms.get(zone);
		if(alarm == null) {
			System.out.println("no alarms found");
			Alarm nullAlarm = new Alarm();
			nullAlarm.zone="";
			nullAlarm.sensor="";
			return nullAlarm;
		}
		else return alarm;
	}
	
	/**
	 * adds a sensor to a zone
	 */
	@Override
	public void add_Sensor(Sensor aSensor, String zone) {
		System.out.println("sensor:" + aSensor.name() +" added to zone " + zone + " in LMS " + getName());
		sensors.put(aSensor.name(),zone);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		//set the log name based on lms  name.
		logFile = name + ".log";
		this.name = name;
	}

	public String getRmcName() {
		return rmcName;
	}

	public void setRmcName(String rmcName) {
		this.rmcName = rmcName;
	}

	@Override
	public String[] get_Sensors() {
		String [] sensorArray = new String[sensors.keySet().size()];
		return sensors.keySet().toArray(sensorArray);
	}

}