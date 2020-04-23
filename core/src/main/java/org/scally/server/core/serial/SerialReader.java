package org.scally.server.core.serial;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;

import java.io.IOException;

public class SerialReader implements SerialDataEventListener {

  private static final char CR = '\r';
  private static final char LF = '\n';

  private StringBuffer buffer = new StringBuffer();
  private SerialLineProcessor processor;

  public SerialReader( SerialLineProcessor processor ) {
    this.processor = processor;
  }

  @Override
  public void dataReceived( SerialDataEvent event) {
    try {
      byte[] data = event.getBytes();

      for( int i = 0; i < data.length; i ++ ) {
        if( data[i] == CR ) {
          // we have a whole line in the buffer, let's process it
          processor.processLine( buffer.toString() );
          buffer.delete( 0, buffer.length() );
        } else if( data[i] != LF ) {
          // just ignore the line feed and append all other characters
          buffer.append( (char) data[i] );
        }
      }
    } catch ( IOException e) {
      // TODO: handle the exception
      e.printStackTrace();
    }
  }
}
