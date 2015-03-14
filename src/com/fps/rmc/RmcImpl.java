package com.fps.rmc;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.ORB;

import com.fps.clients.LmsClient;
import com.fps.clients.SensorsClient;
import com.fps.clients.UserClient;
import com.fps.idl.LMS.Alarm;
import com.fps.idl.RMC.AlarmData;
import com.fps.idl.RMC.RMCPOA;
import com.fps.idl.Sensors.WarningLevel;
import com.fps.rmc.domain.AlarmDo;
import com.fps.rmc.domain.UserDo;
import com.fps.rmc.domain.UserType;
import com.fps.utils.FileUtil;

class RmcImpl extends RMCPOA {
	private ORB orb;
	
	private String name;
	private List<UserDo> users = new ArrayList<UserDo>();
	private List<String> lmsList = new ArrayList<String>();
	private static String userLog;
	/**
	 * Keep alarms and zones
	 */
	List<AlarmDo> alarms = new ArrayList<AlarmDo>();
	
	/**
	 * Called by LMS
	 */
	@Override
	public void raiseAlarm(AlarmData anAlarm) {
		System.out.println("alarm raised by LMS");
		AlarmDo alarm = new AlarmDo();
		alarm.setAlarm(anAlarm);
		alarm.setZone(anAlarm.aReading.zone);
		alarms.add(alarm);
		informUsers(anAlarm);
	}

	private void informUsers(AlarmData anAlarm) {
		for(UserDo user : users){
			informUser(user,anAlarm);
		}
	}

	private void informUser(UserDo user, AlarmData anAlarm) {
		UserClient userClient = new UserClient();
		userClient.informUser(anAlarm);
	}

	@Override
	public void registerAgency(String who, String contact_details, String zone) {
		UserDo user = new UserDo();
		user.setName(who);
		user.setEmail(contact_details);
		user.setZone(zone);
		user.setType(UserType.AGENCY);
		users.add(user);
		writeUserInfo(user);
	}

	@Override
	public void registerUser(String who, String contact_details, String zone) {
		UserDo user = new UserDo();
		user.setName(who);
		user.setEmail(contact_details);
		user.setZone(zone);
		user.setType(UserType.PERSONAL);
		users.add(user);
		writeUserInfo(user);
	}

	private void writeUserInfo(UserDo user){
		FileUtil.writeUserInfo(user, userLog);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		userLog = name + "_users.log";
		this.name = name;
	}
	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	@Override
	public void add_LMS(String name) {
		lmsList.add(name);
	}

	@Override
	public String interogate_Sensor(String who, String zone, String sensor) {
		for (UserDo user : users){
			if(user.getType() == UserType.AGENCY && who.equals(user.getName())){
				SensorsClient sensorClient = new SensorsClient(sensor);
				return "Sensor info(pair,WarningLevel): " + sensorClient.getPairName() + ", " + sensorClient.getWarningLevel().value();
			}
			if(user.getType() == UserType.PERSONAL && who.equals(user.getName()) && user.getZone().equals(zone)){
				SensorsClient sensorClient = new SensorsClient(sensor);
				return "Sensor info(pair,WarningLevel): " + sensorClient.getPairName() + ", " + sensorClient.getWarningLevel().value();
			}
		}
		return "Not found or not authorized to view sensor data.";
	}

	/**
	 * Warning level of the latest alarm.
	 * we get last. we can do a better logic here using date, etc.
	 */
	@Override
	public WarningLevel currentAlarms() {
		
		AlarmDo alarmDo = alarms.get(alarms.size()-1);
		String sensorName = alarmDo.getAlarm().aConfirmingSensor;
		SensorsClient sc = new SensorsClient(sensorName);
		return sc.getWarningLevel();
	}
	
	/**
	 * Get alarms of all LMS's as a string.
	 */
	@Override
	public String getLmsLogs() {
		String out = "";
		for(String lms: lmsList){
			LmsClient lc = new LmsClient(lms);
			List<Alarm> alarms = lc.getAlarmLogs();
			for (Alarm alarm: alarms){
				out = out + alarm.date +"," + alarm.time +"," + alarm.sensor + "," + alarm.zone + "\n";
			}
		}
		return out;
	}

	@Override
	public boolean confirmAlarm(AlarmData alarmData) {
		String pair = alarmData.aConfirmingSensor;
		SensorsClient sc = new SensorsClient(pair);
		if(sc.getWarningLevel().equals(currentAlarms())) return true;
		return false;
	}
}