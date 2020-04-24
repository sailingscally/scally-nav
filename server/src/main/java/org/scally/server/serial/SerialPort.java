package org.scally.server;

import org.scally.server.core.serial.SerialInterface;
import org.scally.server.core.serial.SerialLineProcessor;
import org.scally.server.core.serial.SerialLineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
public class SerialPort {

  private static final String RASPBERRY_PI_ARCHITECTURE = "arm";

  private Logger logger = LoggerFactory.getLogger( SerialPort.class );

  private SerialLineReader reader;
  private SerialInterface serial;

  public SerialPort() {
    reader = new SerialLineReader();

    try {
      logger.info( "Starting serial interface on '{}'.", SerialInterface.DEFAULT_SERIAL_PORT );

      if( System.getProperty( "os.name" ).equals( RASPBERRY_PI_ARCHITECTURE ) ) {
        serial = new SerialInterface();
        serial.addReader( reader );
      } else {
        logger.warn( "Not running on Raspberrry PI, no serial interface will be available." );
      }
    } catch ( IOException e ) {
      logger.error( "Unable to start serial interface.", e );
    }
  }

  public void addProcessor( SerialLineProcessor processor ) {
    reader.addProcessor( processor );
  }

  @PreDestroy
  public void shutdown() throws IOException {
    logger.info( "Shutting down serial interface." );

    if( serial != null ) {
      serial.shutdown();
    }
  }
}
