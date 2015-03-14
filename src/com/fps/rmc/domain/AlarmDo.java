package com.fps.rmc.domain;

import com.fps.idl.RMC.AlarmData;

public class AlarmDo {
	AlarmData alarm;
	String zone;
	public AlarmData getAlarm() {
		return alarm;
	}
	public void setAlarm(AlarmData alarm) {
		this.alarm = alarm;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	
}
