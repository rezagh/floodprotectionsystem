package com.fps.idl.LMS;

/**
* com/fps/idl/LMS/AlarmHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from com/fps/idl/FPS.idl
* Saturday, 14 March 2015 9:23:59 AM EST
*/

public final class AlarmHolder implements org.omg.CORBA.portable.Streamable
{
  public com.fps.idl.LMS.Alarm value = null;

  public AlarmHolder ()
  {
  }

  public AlarmHolder (com.fps.idl.LMS.Alarm initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.fps.idl.LMS.AlarmHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.fps.idl.LMS.AlarmHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.fps.idl.LMS.AlarmHelper.type ();
  }

}
