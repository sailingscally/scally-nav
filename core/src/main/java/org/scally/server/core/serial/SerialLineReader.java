package org.scally.server.core.serial;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class SerialLineReader implements SerialDataEventListener {

  private static final char CR = '\r';
  private static final char LF = '\n';

  private StringBuffer buffer = new StringBuffer();
  private List<SerialLineProcessor> processors = new Vector<>(); // thread safe


  private Logger logger = LoggerFactory.getLogger( SerialLineReader.class );

  public SerialLineReader() {
  }

  public void addProcessor( SerialLineProcessor processor ) {
    processors.add( processor );
  }

  public void removeProcessor( SerialLineProcessor processor ) {
    processors.remove( processor );
  }

  @Override
  public synchronized void dataReceived( SerialDataEvent event) {
    try {
      byte[] data = event.getBytes();

      for( int i = 0; i < data.length; i ++ ) {
        if( data[i] == CR ) {
          // we have a whole line in the buffer, let's process it
          for( SerialLineProcessor processor : processors ) {
            try {
              processor.processLine( buffer.toString() );
            } catch ( StringIndexOutOfBoundsException e ) {
              logger.debug( "Error processing data from serial interface.", e );
            }
          }

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
