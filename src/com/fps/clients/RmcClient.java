package com.fps.clients;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.fps.idl.RMC.*;
import com.fps.utils.Constants;

/**
 * 
 * Client of an RMC server
 *
 */
public class RmcClient {
	RMC rmc;
	
	public RmcClient(String name){
		init(name);
	}
	
	public void init(String lmsName){
		String [] args = new String [4];

		args[0]="-ORBInitialPort";
		args[1]=Constants.port;
		args[2]="-ORBInitialHost";
		args[3]="localhost";
		
		
		try {
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			rmc = RMCHelper.narrow(ncRef.resolve_str(lmsName));
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}

	}
	
	public void raiseAlarm(AlarmData alarm){
		rmc.raiseAlarm(alarm);
	}

}
