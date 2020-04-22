package org.scally.spikes;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;

import java.io.IOException;

public class SerialReader implements SerialDataEventListener {

  private static final String LINE_END = "\r\n";

  private StringBuffer buffer = new StringBuffer();

  @Override
  public void dataReceived( SerialDataEvent event) {
    try {
      String data = event.getAsciiString();
      int index = data.indexOf( LINE_END );

      if( index != -1 ) {
        buffer.append( data.substring( 0, index ) );
        processLine( buffer.toString() );

        buffer.delete( 0, buffer.length() );
        buffer.append( data.substring( index ) );
      } else {
        buffer.append( data );
      }
    } catch ( IOException e) {
      e.printStackTrace();
    }
  }

  private void processLine( String line ) {
    System.out.println( "Line: " + line );
  }
}
