package com.fps.test;

import org.junit.Before;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.fps.idl.LMS.Alarm;
import com.fps.idl.LMS.LMS;
import com.fps.idl.LMS.LMSHelper;
import com.fps.idl.RMC.RMC;
import com.fps.idl.RMC.RMCHelper;
import com.fps.idl.Sensors.Sensor;
import com.fps.idl.Sensors.SensorHelper;
import com.fps.utils.TimeUtil;

public class TestBase {
	NamingContextExt ncRef;

	@Before
	public void init(){
		String [] args = new String [4];

		args[0]="-ORBInitialPort";
		args[1]="53205";
		args[2]="-ORBInitialHost";
		args[3]="localhost";
		
		
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is part of the Interoperable naming Service.
			ncRef = NamingContextExtHelper.narrow(objRef);

			// resolve the Object Reference in Naming

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}

	}

	Sensor getSensor(String sensorName){
		try {
			return SensorHelper.narrow(ncRef.resolve_str(sensorName));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	LMS getLMS(String lmsName){
		try {
			return LMSHelper.narrow(ncRef.resolve_str(lmsName));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	RMC getRMC(String rmcName){
		try {
			return RMCHelper.narrow(ncRef.resolve_str(rmcName));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	Alarm createAlarm(String sensor, String zone){
		Alarm alarm = new Alarm();
		alarm.date=TimeUtil.getDate();
		alarm.time=TimeUtil.getTime();
		alarm.sensor=sensor;
		alarm.zone=zone;
		return alarm;
	}

}
