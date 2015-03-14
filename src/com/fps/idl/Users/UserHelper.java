package com.fps.idl.Users;


/**
* com/fps/idl/Users/UserHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from com/fps/idl/FPS.idl
* Saturday, 14 March 2015 9:23:59 AM EST
*/

abstract public class UserHelper
{
  private static String  _id = "IDL:Users/User:1.0";

  public static void insert (org.omg.CORBA.Any a, com.fps.idl.Users.User that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.fps.idl.Users.User extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (com.fps.idl.Users.UserHelper.id (), "User");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.fps.idl.Users.User read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_UserStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.fps.idl.Users.User value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static com.fps.idl.Users.User narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.fps.idl.Users.User)
      return (com.fps.idl.Users.User)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.fps.idl.Users._UserStub stub = new com.fps.idl.Users._UserStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static com.fps.idl.Users.User unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.fps.idl.Users.User)
      return (com.fps.idl.Users.User)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.fps.idl.Users._UserStub stub = new com.fps.idl.Users._UserStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}