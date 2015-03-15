package com.fps.clients;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.fps.idl.Sensors.*;
import com.fps.utils.Constants;

/**
 * 
 * Client of a sensor server
 *
 */
public class SensorsClient {
	Sensor sensor;
	
	public SensorsClient(String name){
		init(name);
	}
	
	public void init(String name){
		String [] args = new String [4];

		args[0]="-ORBInitialPort";
		args[1]=Constants.port;
		args[2]="-ORBInitialHost";
		args[3]="localhost";
		
		
		try {
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			sensor = SensorHelper.narrow(ncRef.resolve_str(name));
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

	public WarningLevel getWarningLevel(){
		return sensor.current();
	}
	
	public String getPairName(){
		return sensor.get_pair_name();
	}
	
	
}
