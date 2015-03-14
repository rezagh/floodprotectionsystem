package com.fps.user;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.fps.idl.RMC.*;
import com.fps.idl.Users.User;
import com.fps.idl.Users.UserHelper;
import com.fps.utils.Constants;

public class UserServer {

	public static void main(String args[]) {
		try {

			String name="user_1";
			
			String argu[] = new String [4];
			argu[0]="-ORBInitialPort";
			argu[1]=Constants.port;
			argu[2]="-ORBInitialHost";
			argu[3]="localhost";

			// create and initialize the ORB
			ORB orb = ORB.init(argu, null);
			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// create servant and register it with the ORB
			UserImpl userImpl = new UserImpl();
			userImpl.setORB(orb);

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(userImpl);
			User href = UserHelper.narrow(ref);

			// get the root naming context NameService invokes the name service
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt which is part of the Interoperable Naming Service (INS) specification.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);
			//Sensor sensorImpl = SensorHelper.narrow(ncRef.resolve_str(name));
			//sensorImpl.name("");
			
			System.out.println("User: " + name + " ready and waiting ...");

			// wait for invocations from clients
			orb.run();
		}

		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}

		System.out.println("User Exiting ...");

	}
}