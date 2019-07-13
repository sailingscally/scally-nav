package org.scally.server.core.sensors;

public class BME280NotFoundException extends Exception {

  public BME280NotFoundException( String message ) {
    super( message );
  }

  public BME280NotFoundException( String message, Exception e ) {
    super( message, e );
  }
}
