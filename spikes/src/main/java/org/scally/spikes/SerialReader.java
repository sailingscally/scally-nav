package org.scally.spikes;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;

import java.io.IOException;

public class SerialReader implements SerialDataEventListener {

  private StringBuffer buffer = new StringBuffer();

  @Override
  public void dataReceived( SerialDataEvent event) {
    try {
      System.out.println( "RX: " + event.getAsciiString() );

      buffer.append( event.getAsciiString() );

      if( buffer.toString().endsWith( "\n" ) ) {
        System.out.println( "Line: " + buffer.toString() );
        buffer.delete( 0, buffer.length() );
      }
    } catch ( IOException e) {
      e.printStackTrace();
    }
  }
}
