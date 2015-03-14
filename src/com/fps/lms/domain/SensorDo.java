package com.fps.lms.domain;

import com.fps.idl.Sensors.Sensor;

/**
 *	domain object that keeps sensors and their zones 
 *
 */
public class SensorDo {
	Sensor sensor;
	String[] zones;
	
	public Sensor getSensor() {
		return sensor;
	}
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	public String[] getZones() {
		return zones;
	}
	public void setZones(String[] zones) {
		this.zones = zones;
	}
	
	
}
