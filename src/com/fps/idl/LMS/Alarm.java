package com.fps.idl.LMS;


/**
* com/fps/idl/LMS/Alarm.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from com/fps/idl/FPS.idl
* Saturday, 14 March 2015 9:23:59 AM EST
*/

public final class Alarm implements org.omg.CORBA.portable.IDLEntity
{
  public int time = (int)0;
  public int date = (int)0;
  public String sensor = null;
  public String zone = null;

  public Alarm ()
  {
  } // ctor

  public Alarm (int _time, int _date, String _sensor, String _zone)
  {
    time = _time;
    date = _date;
    sensor = _sensor;
    zone = _zone;
  } // ctor

} // class Alarm
