package com.fps.interogator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.fps.idl.LMS.LMS;
import com.fps.idl.LMS.LMSHelper;
import com.fps.idl.RMC.RMC;
import com.fps.idl.RMC.RMCHelper;
import com.fps.idl.Sensors.Sensor;
import com.fps.idl.Sensors.SensorHelper;
import com.fps.utils.Constants;

public class Menu {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	NamingContextExt ncRef;

	String readLine(){
    	try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
    }
    

	public Menu(){
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
}