package org.scally.spikes;

import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;

import java.io.IOException;

/**
 * This example code demonstrates how to perform serial communications using the Raspberry Pi.
 *
 * @author Robert Savage
 */
public class SerialExample {

  public static void main(String args[]) throws Exception {
    final Serial serial = SerialFactory.createInstance();

    serial.addListener(new SerialDataEventListener() {
      @Override
      public void dataReceived( SerialDataEvent event) {
        try {
          System.out.println("[HEX DATA]   " + event.getHexByteString());
          System.out.println("[ASCII DATA] " + event.getAsciiString());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    // create serial config object
    SerialConfig config = new SerialConfig();
    config.device("/dev/serial0").baud( Baud._4800).dataBits( DataBits._8).parity( Parity.NONE).stopBits( StopBits._1).flowControl( FlowControl.NONE);

    // open the default serial device/port with the configuration settings
    serial.open(config);

    while ( true ) {
      Thread.sleep( 10000 );
    }
  }
}
