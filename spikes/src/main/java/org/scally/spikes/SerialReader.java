package org.scally.spikes;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;

import java.io.IOException;

public class SerialReader implements SerialDataEventListener {

  private static final char CR = '\r';
  private static final char LF = '\n';

  private StringBuffer buffer = new StringBuffer();

  @Override
  public void dataReceived( SerialDataEvent event) {
    try {
      byte[] data = event.getBytes();

      for( int i = 0; i < data.length; i ++ ) {
        if( data[i] == CR ) {
          // we have a whole line in the buffer, let's process it
          processLine( buffer.toString() );
          buffer.delete( 0, buffer.length() );
        } else if( data[i] == LF ) {
          // just ignore the line feed
        } else {
          buffer.append( (char) data[i] );
        }
      }
    } catch ( IOException e) {
      e.printStackTrace();
    }
  }

  private void processLine( String line ) {
    System.out.println( "Line: " + line );
  }
}
