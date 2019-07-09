package org.scally.server.core.sensors;

public class DeviceNotFoundException extends Exception {

  public DeviceNotFoundException( String message ) {
    super( message );
  }

  public DeviceNotFoundException( String message, Exception e ) {
    super( message, e );
  }
}
