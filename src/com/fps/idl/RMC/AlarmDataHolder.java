package com.fps.idl.RMC;

/**
* com/fps/idl/RMC/AlarmDataHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from com/fps/idl/FPS.idl
* Saturday, 14 March 2015 9:23:59 AM EST
*/

public final class AlarmDataHolder implements org.omg.CORBA.portable.Streamable
{
  public com.fps.idl.RMC.AlarmData value = null;

  public AlarmDataHolder ()
  {
  }

  public AlarmDataHolder (com.fps.idl.RMC.AlarmData initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.fps.idl.RMC.AlarmDataHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.fps.idl.RMC.AlarmDataHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.fps.idl.RMC.AlarmDataHelper.type ();
  }

}
