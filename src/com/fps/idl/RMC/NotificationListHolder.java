package com.fps.idl.RMC;


/**
* com/fps/idl/RMC/NotificationListHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from com/fps/idl/FPS.idl
* Friday, 13 March 2015 4:58:56 PM EST
*/

public final class NotificationListHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public NotificationListHolder ()
  {
  }

  public NotificationListHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.fps.idl.RMC.NotificationListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.fps.idl.RMC.NotificationListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.fps.idl.RMC.NotificationListHelper.type ();
  }

}
