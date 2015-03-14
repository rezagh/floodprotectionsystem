Flood Protection System (CORBA - Freelancer)

Technology Requirements:
	Java 1.7
	Batch files are for Windows
	Eclipse IDE
	Junit 4 library - from winthin eclipse
	
Running:
	src\make_IDLs.bat: generates stubs from idl
	deploy_LMS.bat: runs corba server for 2 LMS servers. they know their own name and their RMC.
	deploy_Sensors.bat: runs corba server for few sensors. They know their own name and their LMS.
	deploy_RMC.bat: runs corba server for a single RMC.
	deploy_Users.bat: runs corba server simulating a user that can receive alarms and email notifications
	call_all.bat runs all above
	run_ORB.bat: runs the ORB for Corba servers
	
	Interogator is the single client for everything: sensors, LMS and RMC, users. It also acts as the sensor hardware simulator. It is a menu based command line application.
	There are some unit tests under the test package
	 

Assumptions:
	
	General:
		it is best to use a database but we save information (logs) in text files.
		Keeping everything in one project to make testing simpler. In reality we have to have separate deployables with some shared library.
		For time we show only hour of the day 
		for date we show only day of the month
		sensors, LMS's, RMC's they all know their "names" which is set at startup.
		
	Sensors:
		sensors know their LMS name. this is necessary for the sensor to be able to raise alarm
	
		water level assumption:1-10
			1,2,3:low
			4,5,6 average
			7-10 high
		
		only if water level is high then sensor sends alarm to lms

	RMC
		we only have one RMC (rmc_1) for simplicity
	
	LMS
		LMS raises alarm to RMC only when pair confirms a high reading	
		LMS knows its RMC name
		
components and requirements:

	sensor: is a server
		**read water level 
		**sensors are installed in pairs
		**has an alarm status
		**issues warnings to LMS
		**maintain internal log of reading and current alarm status
		**Only one alarm will be serviced at a time.
		**maintains an alarm status : can switch on/off/reset
	
	LMS: server
		**LMS receives signals/alarms from a series of sensors
		**stores readings in a log
		**should any sensor raise an alarm, this is confirmed by the LMS requesting a reading from the originating sensor's pair.
		**Any confirmed alarms automatically raise an alarm at the Regional Monitoring Centre (RMC) where an an operative will inform the Police, etc
		**Registers an alarm from a sensor upon request 
		**Maintains a log of sensor alerts
		**retrieves the log upon request
		**triggers an alarm at a RMC
		**maintains its own sensor zone coverage
		**The number of Local Monitoring Stations is fixed=2
		**The number of Sensors per LMS can be fixed. One sensor pair per LMS generating levels with keyboard input
		**provides the current alarm status upon request
					
	RMC: server
		
		**The regional system (RMC) will also maintain a database of contact details for residents and commercial properties that wish to be informed when alarms are raised. 
		**The public can register their wish to be informed of alarms via the Regional Server system.
		**Handles authenticated alarms by alerting all registered agencies (police etc) and registered users
		**Authorised property owners may be allowed access to the sensors that are installed to protect their own properties
		**The centralised (by region) RMC system can interrogate the LMS's to ascertain the current status of warnings and to retrieve appropriate log details.
		**Retrieves a report on current alarms throughout the region upon request.
		
	Clients
		**To interrogate the servers and demonstrate the cooperation required between server objects	 
		**A simple client that enables interrogation of the Sensor
		**A simple program taking the role of a sensor that generates real-time alarms, are to be developed
		**A simple client program that can be used to obtain status information from the RMC is also required
		**A simple server that represents a registered users home PC might also be considered.
		**Registered agencies should be able to interrogate any sensor throughout the region
		
		
Running/testing/presentation scenario:	
- interogate sensor - M1 - empty
- add sensor to lms zone - m10
- register user - m21
- register agency - m22
- register lms - m23
- inject to sensor 1 - M2
- inject to sensor 2 - M2
- interogate sensors again - M1
- view lms alarms - M11
- get lms logs - M12
- get rmc warning level - M20
- get lms logs by rmc - m24	
- interogate sensor by agency - m30
- interogate sensor by owner - m31
- set sensor status - m3
- interoget sensor - m1	

