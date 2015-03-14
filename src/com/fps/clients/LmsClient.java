package com.fps.clients;

import java.util.Arrays;
import java.util.List;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.fps.idl.LMS.Alarm;
import com.fps.idl.LMS.LMS;
import com.fps.idl.LMS.LMSHelper;
import com.fps.idl.Sensors.Reading;
import com.fps.utils.Constants;

public class LmsClient {
	LMS lms;
	
	public LmsClient(String name){
		init(name);
	}
	
	public void init(String lmsName){
		String [] args = new String [4];

		args[0]="-ORBInitialPort";
		args[1]=Constants.port;
		args[2]="-ORBInitialHost";
		args[3]="localhost";
		
		
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is part of the Interoperable naming Service.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			lms = LMSHelper.narrow(ncRef.resolve_str(lmsName));

			
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}

	}

	public List<String> getSensors(){
		 String[] sensors = lms.get_Sensors();
		 return Arrays.asList(sensors);
	}

	public List<Alarm> getAlarmLogs(){
		return Arrays.asList(lms.log());
	}
	
	public void raiseAlarmToLms(Reading reading, String name){
		Alarm alarm = new Alarm();
		alarm.date=reading.date;
		alarm.time=reading.time;
		alarm.sensor=name;
		alarm.zone="";
		lms.raise_Alarm(alarm);
	}
}
