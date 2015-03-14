package com.fps.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.fps.idl.Sensors.Reading;
import com.fps.idl.Sensors.Sensor;
import com.fps.idl.Sensors.SensorHelper;
import com.fps.idl.Sensors.Status;

public class SensorTester extends TestBase{

	@Test
	public void injectReadingToSensors(){
		int readingLevel = 6;
		String sensorName = "sensor_a1";
		injectReadingToSensor(sensorName, readingLevel);
		Reading reading = getSensorReading(sensorName);
		Assert.assertTrue(reading.reading == readingLevel);
	}

	@Test
	public void readSensorsReadings(){
		getSensorReading("sensor_1");
	}
	
	@Test
	public void setSensorStatus(){
		Status stat = Status.ON;
		String sensor = "sensor_1";
		
		setSensorStatus(sensor,stat);
		Assert.assertTrue(getSensorStatus("sensor_1").value()==stat.value());
	}
	
	
	private Status getSensorStatus(String name) {
		System.out.println("getting status from sensor:" + name );
		Sensor sensor = getSensor(name);
		Status stat= sensor.get_sensor_status();
		System.out.println("status is:" + stat.value() );
		return stat;
	}

	private void setSensorStatus(String sensorName, Status status) {
		System.out.println("setting status " + status.value() + " to sensor" + sensorName );
		Sensor sensor = getSensor(sensorName);
		sensor.set_sensor_status(status);
	}


	
	public void injectReadingToSensor(String sensorName, int readingLevel){
		System.out.println("Injecting reading: " + readingLevel +" to Sensor:" + sensorName);
		Sensor sensor = getSensor(sensorName);
		Reading reading = new Reading();
		reading.reading= readingLevel;
		sensor.currentReading(reading);
	}
	
	private Reading getSensorReading(String sensorName) {
		Sensor sensor = getSensor(sensorName);
		Reading currentReading = sensor.currentReading();
		System.out.println("Reading from sensor:" + sensorName);
		System.out.println(sensorName);
		System.out.println("reading:" +currentReading.reading);
		System.out.println("date:" +currentReading.date);
		System.out.println("time:" +currentReading.time);
		
		return currentReading;
	}
}