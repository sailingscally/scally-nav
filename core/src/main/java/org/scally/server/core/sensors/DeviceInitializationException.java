package org.scally.server.core.sensors;

public class DeviceInitializationException extends Exception {

  public DeviceInitializationException( String message ) {
    super( message );
  }

  public DeviceInitializationException( String message, Exception e ) {
    super( message, e );
  }
}
