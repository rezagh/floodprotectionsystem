package com.fps.test;

import org.junit.Test;

import com.fps.idl.LMS.LMS;
import com.fps.idl.RMC.RMC;
import com.fps.idl.Sensors.Sensor;

public class RmcTester extends TestBase{

	@Test
	public void registerAgency(){
		String rmcNAme = "rmc_1";
		RMC rmc = getRMC(rmcNAme);
		rmc.registerAgency("jason", "json@gmail.com", "zone_a");
		
	}


}