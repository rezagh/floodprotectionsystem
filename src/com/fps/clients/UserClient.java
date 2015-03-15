package com.fps.clients;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.fps.idl.RMC.AlarmData;
import com.fps.idl.Users.User;
import com.fps.idl.Users.UserHelper;
import com.fps.utils.Constants;

/**
 * 
 * Client of a user server. We simulated a user as a corba server for simplicity.
 *
 */
public class UserClient {
	User user;
	
	public UserClient(){
		init();
	}
	
	public void init(){
		String name="user_1";
		
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
			user = UserHelper.narrow(ncRef.resolve_str(name));

			// resolve the Object Reference in Naming

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}

	}

	public void informUser(AlarmData alarm){
		user.receive_email(alarm);
	}

}
