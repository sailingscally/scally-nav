package org.scally.spikes;

import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;

public class SerialInterface {

  public static void main( String args[] ) throws Exception {
    SerialReader reader = new SerialReader();

    SerialConfig config = new SerialConfig();
    config.device( "/dev/serial0" )
      .baud( Baud._4800 )
      .dataBits( DataBits._8 )
      .parity( Parity.NONE )
      .stopBits( StopBits._1 )
      .flowControl( FlowControl.NONE );

    Serial serial = SerialFactory.createInstance();
    serial.addListener( reader );

    serial.open( config );

    while ( true ) {
      Thread.sleep( 10000 );
    }
  }
}
