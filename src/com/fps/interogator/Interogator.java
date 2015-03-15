package com.fps.interogator;

import java.io.IOException;

import com.fps.idl.LMS.Alarm;
import com.fps.idl.LMS.LMS;
import com.fps.idl.RMC.RMC;
import com.fps.idl.Sensors.Reading;
import com.fps.idl.Sensors.Sensor;
import com.fps.idl.Sensors.Status;
import com.fps.idl.Sensors.WarningLevel;
import com.fps.utils.TimeUtil;

/**
 * 
 * This act as a client for all the servers in this application.
 * It also acts as a simulator for a sernsor hardware to inject reading levels into sensor servers.
 *
 */
public class Interogator extends Menu{
	String rmcName="rmc_1";
    RMC rmc = getRMC(rmcName);
	
	public static void main(String args[]){
		new Interogator();
	}
	
	Interogator (){
		try {
			mainMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void mainMenu() throws NumberFormatException, IOException {

	    while (true) {
	        printMainMenu();
            int input = Integer.parseInt(br.readLine());
	        switch (input) {
	            case 1: interogateSensor();break;
	            case 2: injectReadingToSensor();break;
	            case 3: setSensorStatus();break;
	            
	            case 10: addSensorToLMS();break;
	            case 11: viewLmsAlarmStatusForZone();break;
	            case 12: getLmsLogs();break;
	            
	            case 20: getRmcWarningLevel();break;
	            case 21: registerUser();break;
	            case 22: registerAgency();break;
	            case 23: registerLMS();break;
	            case 24: getLmsLogsForRMC();break;

	            case 30: interogateSensorByAgency();break;
	            case 31: interogateSensorByOwner();break;
	            
	            case 50: System.exit(0);break;
	            default: System.out.println("Wrong choice");
	        }
	    }
	}

	private void printMainMenu() {
		System.out.println("\n---------------Sensor Menu------------------");
		System.out.println("1- Interogate Sernsors");
		System.out.println("2- Inject Reading to Sernsors");
		System.out.println("3- Set Sensor Status");
		System.out.println("---------------LMS Menu---------------------");
		System.out.println("10- Add Sensor to LMS Zone");
		System.out.println("11- View LMS Confirmed Alarms Status for Zone");
		System.out.println("12- Get LMS All Alarm Logs");
		System.out.println("---------------RMC Menu---------------------");
		System.out.println("20- Get RMC Warning Level");
		System.out.println("21- Register User");
		System.out.println("22- Register Agency");
		System.out.println("23- Register LMS");
		System.out.println("24- Get LMS Logs");
		System.out.println("---------------User Menu--------------------");
		System.out.println("30- Interogate Sensor by Agency");
		System.out.println("31- Interogate Sensor by Property Owner");
		System.out.println("--------------------------------------------");
		System.out.println("50- Exit");
	}

	private void interogateSensor() {
		System.out.println("Sensor name?");
        String sensorName = readLine();
        Sensor sensor = getSensor(sensorName);
        System.out.println("name:" + sensor.name());
        System.out.println("warning level (unknown:0,low:1,average:2,high:3):" + sensor.current().value());
        System.out.println("pair name:" + sensor.get_pair_name());
        System.out.println("Status (ON:0,OFF:1,RESET:2):" + sensor.get_sensor_status().value());
        Reading reading = sensor.currentReading();
        if(reading != null) System.out.println("current reading (date,time,value): " + reading.date + "," + reading.time + "," + reading.reading);
	}

	private void injectReadingToSensor() {
		System.out.println("Sensor name?");
        String sensorName = readLine();
		System.out.println("Reading (1-10)?");
        String readingSrc = readLine();
        Sensor sensor = getSensor(sensorName);
        Reading reading = new Reading();
        reading.date = TimeUtil.getDate();
        reading.time = TimeUtil.getTime();
        reading.reading = Integer.valueOf(readingSrc);
        sensor.currentReading(reading);
	}

	private void setSensorStatus() {
		System.out.println("Sensor name?");
        String sensorName = readLine();
        Sensor sensor = getSensor(sensorName);
		System.out.println("ON:0,OFF:1,RESET:2?");
        String stat = readLine();
        sensor.set_sensor_status(Status.from_int(Integer.valueOf(stat)));
	}

	private void addSensorToLMS() {
		System.out.println("LMS name?");
        String lmsName = readLine();
        LMS lms = getLMS(lmsName);
		System.out.println("LMS zone (zone_a,zone_b, etc)?");
        String zone = readLine();
		System.out.println("Sensor name?");
        String sensor = readLine();
        Sensor aSensor = getSensor(sensor);
        lms.add_Sensor(aSensor, zone);
	}
	
	private void viewLmsAlarmStatusForZone() {
		System.out.println("LMS name?");
        String lmsName = readLine();
        LMS lms = getLMS(lmsName);
		System.out.println("LMS zone (zone_a,zone_b, etc)?");
        String zone = readLine();
        Alarm alarm = lms.get_currrent_alarm_status(zone);
		if (alarm == null) System.out.println("No alarm!");
		else {
			System.out.println("date,time,sensor: " + alarm.date + "," + alarm.time + "," + alarm.sensor);
		}
		
		//TODO does not work
	}


	private void getLmsLogs() {
		System.out.println("LMS name?");
        String lmsName = readLine();
        LMS lms = getLMS(lmsName);
        Alarm[] alarms =lms.log();
        System.out.println("date,time,sensor");
        for(Alarm alarm: alarms){
        	System.out.println(alarm.date+","+alarm.time + "," + alarm.sensor);
        }
	}


	private void registerAgency() {
		System.out.println("Agency Name?");
        String name = readLine();
		System.out.println("Email?");
        String email = readLine();
		System.out.println("zone?");
        String zone = readLine();

        rmc.registerAgency(name, email, zone);
		System.out.println("registered.");
	}

	private void registerUser() {
		System.out.println("User Name?");
        String name = readLine();
		System.out.println("Email?");
        String email = readLine();
		System.out.println("zone?");
        String zone = readLine();

        rmc.registerUser(name, email, zone);
		System.out.println("registered.");
		
	}

	private void getRmcWarningLevel() {
        WarningLevel level = rmc.currentAlarms();
        System.out.println("Current warning level (unknown:0,low:1,average:2,high:3):" + level.value());
	}
	
	private void registerLMS() {
		System.out.println("LMS name?");
        String lms = readLine();
        rmc.add_LMS(lms);
	}


	private void interogateSensorByAgency() {
		System.out.println("Agency name?");
        String agency = readLine();
		System.out.println("Sensor name?");
        String sensor = readLine();
        String out = rmc.interogate_Sensor(agency, "", sensor);
        System.out.println(out);
	}
	

	private void interogateSensorByOwner() {
		System.out.println("Agency name?");
        String agency = readLine();
		System.out.println("Sensor name?");
        String sensor = readLine();
		System.out.println("Zone name?");
        String zone = readLine();
        String out = rmc.interogate_Sensor(agency, zone, sensor);
        System.out.println(out);
		
	}

	private void getLmsLogsForRMC() {
        System.out.println(rmc.getLmsLogs());
	}
}