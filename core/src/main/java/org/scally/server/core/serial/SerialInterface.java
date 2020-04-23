package org.scally.server.core.serial;

import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;

import java.io.IOException;

public class SerialInterface {

  /**
   * This is the default serial port on the Raspberry PI Zero W
   */
  public static final String DEFAULT_SERIAL_PORT = "/dev/serial0";

  /**
   * Default configuration for NMEA 0183 devices such as the Raymarine GPS receiver in use
   */
  public static final Baud DEFAULT_DATA_RATE = Baud._4800;
  public static final DataBits DEFAULT_DATA_BITS = DataBits._8;
  public static final Parity DEFAULT_PARITY = Parity.NONE;
  public static final StopBits DEFAULT_STOP_BITS = StopBits._1;
  public static final FlowControl DEFAULT_FLOW_CONTROL = FlowControl.NONE;

  private Serial serial;
  private SerialConfig config;

  public SerialInterface() {
    this( DEFAULT_SERIAL_PORT, DEFAULT_DATA_RATE, DEFAULT_DATA_BITS, DEFAULT_PARITY, DEFAULT_STOP_BITS, DEFAULT_FLOW_CONTROL );
  }

  public SerialInterface( String device, Baud rate, DataBits bits, Parity parity, StopBits stop, FlowControl flow ) {
    config = new SerialConfig();
    config.device( device ).baud( rate ).dataBits( bits ).parity( parity ).stopBits( stop ).flowControl( flow );

    serial = SerialFactory.createInstance();
  }

  public void start( SerialReader reader ) throws IOException {
    serial.addListener( reader );
    serial.open( config );
  }

  public void shutdown() throws IOException {
    if( serial.isOpen() ) {
      serial.close();
    }
  }
}
