package com.fps.idl.Sensors;

/**
* com/fps/idl/Sensors/WarningLevelHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from com/fps/idl/FPS.idl
* Saturday, 14 March 2015 9:23:59 AM EST
*/

public final class WarningLevelHolder implements org.omg.CORBA.portable.Streamable
{
  public com.fps.idl.Sensors.WarningLevel value = null;

  public WarningLevelHolder ()
  {
  }

  public WarningLevelHolder (com.fps.idl.Sensors.WarningLevel initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.fps.idl.Sensors.WarningLevelHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.fps.idl.Sensors.WarningLevelHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.fps.idl.Sensors.WarningLevelHelper.type ();
  }

}