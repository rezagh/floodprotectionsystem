package com.fps.user;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.ORB;

import com.fps.idl.RMC.AlarmData;
import com.fps.idl.Users.UserPOA;
import com.fps.rmc.domain.UserDo;

class UserImpl extends UserPOA {
	private ORB orb;
	
	private String name;
	private List<UserDo> users = new ArrayList<UserDo>();
	private static String userLog;
	
	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	@Override
	public void receive_email(AlarmData alarm) {
		System.out.println("user received alarm!!!");
	}
}
