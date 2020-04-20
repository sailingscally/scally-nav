package org.scally.spikes;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

public class MyADC2 {

  public static void main( String[] args ) throws Exception {
    System.out.println( "---- Channel #0 ----" );
    read( (byte) 0b01000000 );
    System.out.println( "---- Channel #1 ----" );
    read( (byte) 0b01010000 );
    System.out.println( "---- Channel #2 ----" );
    read( (byte) 0b01100000 );
    System.out.println( "---- Channel #3 ----" );
    read( (byte) 0b01110000 );
  }

  private static void read( byte channel ) throws Exception {
    I2CBus bus = I2CFactory.getInstance( I2CBus.BUS_1 );
    I2CDevice device = bus.getDevice( 0x48 );

    byte options = (byte) ( (byte) 0b10000000 | channel | (byte) 0b00000010 | (byte) 0b1 ); // begin / channel 1, gain = 1, single
    byte defaults = (byte) 0b00000011; // 128 sps / traditional comp / active low / non latching / disable comp

    System.out.format( "Configuration register: 0b%s%s [write]\n", Integer.toBinaryString( options & 0xFF ), Integer.toBinaryString( defaults & 0xFF ) );
    System.out.format( "Configuration register: 0x%s%s [write]\n", Integer.toHexString( options & 0xFF ), Integer.toHexString( defaults & 0xFF ) );
    device.write( 0x01, new byte[] { options, defaults }, 0, 2 ); // write two bytes

    byte[] config = new byte[2];
    device.read( 0x01, config, 0, config.length );
    System.out.format( "Configuration register: 0b%s%s [read]\n", Integer.toBinaryString( config[0] & 0xFF ), Integer.toBinaryString( config[1] & 0xFF ) );
    System.out.format( "Configuration register: 0x%s%s [read]\n", Integer.toHexString( config[0] & 0xFF ), Integer.toHexString( config[1] & 0xFF ) );

    Thread.sleep( 2000 ); // wait for the conversion to complete
    System.out.println();

    byte[] data = new byte[2];
    device.read( 0x00, data, 0, data.length );
    System.out.format( "Conversion register: 0x%s%s\n", Integer.toHexString( data[0] & 0xFF ), Integer.toHexString( data[1] & 0xFF ) );

    int value = ( data[0] & 0xFF ) << 4 | ( data[1] & 0xFF ) >> 4;

    if( ( value & 0x800 ) != 0 ) { // check for sign bit (this is a two's complement)
      value -= 1 << 12;
    }

    System.out.format( "Raw ADC value: %d\n", value );

    double voltage = value / 2048.0 * 4.096;
    System.out.format( "Voltage: %.2f\n", voltage );
  }
}
