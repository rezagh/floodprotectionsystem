package com.fps.idl.Sensors;


/**
* com/fps/idl/Sensors/Reading.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from com/fps/idl/FPS.idl
* Saturday, 14 March 2015 9:23:59 AM EST
*/

public final class Reading implements org.omg.CORBA.portable.IDLEntity
{
  public int time = (int)0;
  public int date = (int)0;
  public int reading = (int)0;

  public Reading ()
  {
  } // ctor

  public Reading (int _time, int _date, int _reading)
  {
    time = _time;
    date = _date;
    reading = _reading;
  } // ctor

} // class Reading
