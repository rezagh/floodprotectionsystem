package com.fps.idl.RMC;


/**
* com/fps/idl/RMC/AlarmDataHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from com/fps/idl/FPS.idl
* Saturday, 14 March 2015 9:23:59 AM EST
*/

abstract public class AlarmDataHelper
{
  private static String  _id = "IDL:RMC/AlarmData:1.0";

  public static void insert (org.omg.CORBA.Any a, com.fps.idl.RMC.AlarmData that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.fps.idl.RMC.AlarmData extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = com.fps.idl.LMS.AlarmHelper.type ();
          _members0[0] = new org.omg.CORBA.StructMember (
            "aReading",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "aConfirmingSensor",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.fps.idl.RMC.AlarmDataHelper.id (), "AlarmData", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.fps.idl.RMC.AlarmData read (org.omg.CORBA.portable.InputStream istream)
  {
    com.fps.idl.RMC.AlarmData value = new com.fps.idl.RMC.AlarmData ();
    value.aReading = com.fps.idl.LMS.AlarmHelper.read (istream);
    value.aConfirmingSensor = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.fps.idl.RMC.AlarmData value)
  {
    com.fps.idl.LMS.AlarmHelper.write (ostream, value.aReading);
    ostream.write_string (value.aConfirmingSensor);
  }

}
