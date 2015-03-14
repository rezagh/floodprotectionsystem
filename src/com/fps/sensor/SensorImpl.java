package com.fps.sensor;

import org.omg.CORBA.ORB;

import com.fps.clients.LmsClient;
import com.fps.idl.Sensors.Reading;
import com.fps.idl.Sensors.SensorPOA;
import com.fps.idl.Sensors.Status;
import com.fps.idl.Sensors.WarningLevel;
import com.fps.utils.FileUtil;
import com.fps.utils.TimeUtil;

class SensorImpl extends SensorPOA {
	private ORB orb;
	
	private Reading reading;
	private String name;
	private String lmsName;
	private Status status;
	private String pairName;
	private static String readingLogFile;
	private static String alarmsLogFile;
	private String zone="";//the zone this sensor belongs to. Is set externally
	/**
	 * Give the current warning level
	 */
	@Override
	public WarningLevel current() {
		if (status==Status.OFF) {
			System.out.println("Sensor is OFF. can not set warning leve.");
			return WarningLevel.UNKNOWN;
		}
		//TODO move to service layer
		if (reading != null){
			if(reading.reading <=3) return WarningLevel.MINOR;
			if(reading.reading >3 && reading.reading <=6) return WarningLevel.AVERAGE;
			if(reading.reading >6) return WarningLevel.HIGH;
		}
		return WarningLevel.UNKNOWN;
	}

	/**
	 * Give the current reading
	 */
	@Override
	public Reading currentReading() {
		if (status==Status.OFF) {
			System.out.println("Sensor is OFF.can not set reading.");
			return new Reading();
		}
		if (reading == null) return new Reading();
		return reading;
	}

	/**
	 * Set/Inject a reading from the sensor hardware (a unit test class)
	 */
	@Override
	public void currentReading(Reading newCurrentReading) {
		if (status==Status.OFF) {
			System.out.println("Sensor is OFF");
			return;
		}
		this.reading = newCurrentReading;
		
		this.reading.date = TimeUtil.getDate();
		this.reading.time = TimeUtil.getTime();
		
		System.out.println("setting new reading to sensor: " + this.reading.reading);
		writeReadingLog(reading);
		//only raise alarm when reading is high
		WarningLevel level = current();
		if(WarningLevel.HIGH.equals(level)){
			raiseAlarmToLMS(reading);
		}
		writeAlarmLog(level);
	}
	
	/**
	 *	Raise alarm to LMS 
	 */
	private void raiseAlarmToLMS(Reading reading2) {
		try{
			LmsClient lmsClient = new LmsClient(getLmsName());
			lmsClient.raiseAlarmToLms(reading2,this.name);
		}catch(Throwable t){
			System.out.println("Can not raise alarm to LMS");
			t.printStackTrace();
		}
		
	}
	

	/**
	 * set sensor to on/off/reset
	 */
	@Override
	public void set_sensor_status(Status stat) {
		this.status = stat;
		
		if(stat==Status.OFF){
			System.out.println("sensor set to OFF");
		}

		if(stat==Status.ON){
			System.out.println("sensor set to ON");
			
		}
		if(stat==Status.RESET){
			System.out.println("sensor set to reset. readings will reset.");
			this.reading = new Reading();
			current(WarningLevel.UNKNOWN);
		}
		
		
		
	}

	/**
	 * Get the sensor status
	 */
	@Override
	public Status get_sensor_status() {
		return this.status;
	}

	/**
	 * Get the name of the pair for this sensor. set at startup
	 */
	@Override
	public String get_pair_name() {
		return this.pairName;
	}
	
	/**
	 * warn level should be calculated not injected 
	 */
	@Override
	public void current(WarningLevel newCurrent) {
		//No impl required
	}
	

	@Override
	public String zone() {
		return zone;
	}

	@Override
	public void zone(String newZone) {
		this.zone = newZone;
	}

	private void writeReadingLog(Reading reading){
		FileUtil.writeSensorReadingLog(reading, readingLogFile);
	}
	
	private void writeAlarmLog(WarningLevel level){
		FileUtil.writeSensorAlarmLog(level, alarmsLogFile);
	}

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}
 
	
	@Override
	public String name() {
		return this.name;
	}

	public void setName(String name) {
		//set the file name based on sensor name
		readingLogFile = name + "_reading.log";
		alarmsLogFile = name + "_alarm.log";
		this.name = name;
	}


	public String getLmsName() {
		return lmsName;
	}


	public void setLmsName(String lmsName) {
		this.lmsName = lmsName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPairName() {
		return pairName;
	}

	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
}