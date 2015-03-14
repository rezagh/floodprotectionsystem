package com.fps.sensor;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.fps.idl.Sensors.Sensor;
import com.fps.idl.Sensors.SensorHelper;
import com.fps.idl.Sensors.Status;
import com.fps.utils.Constants;

public class SensorServer {

	public static void main(String args[]) {
		try {

			final String name = args[0];
			final String lmsName = args[1];
			final String pairName = args[2];
			
			String argu[] = new String [7];
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
			SensorImpl sensorImpl = new SensorImpl();
			sensorImpl.setORB(orb);
			sensorImpl.setName(name);
			sensorImpl.setLmsName(lmsName);
			sensorImpl.setStatus(Status.ON);
			sensorImpl.setPairName(pairName);
			
			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(sensorImpl);
			Sensor href = SensorHelper.narrow(ref);

			// get the root naming context NameService invokes the name service
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt which is part of the Interoperable Naming Service (INS) specification.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);
			
			
			System.out.println("Sensor: " + name + " on " + lmsName + " ready and waiting ...");

			// wait for invocations from clients
			orb.run();
		}

		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}

		System.out.println("Sensor Exiting ...");
	}
}