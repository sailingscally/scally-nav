package org.scally.server.core.sensors.adc;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.scally.server.core.sensors.DeviceNotFoundException;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

/**
 * Datasheet can be found at: https://cdn-shop.adafruit.com/datasheets/ads1015.pdf
 */
public class ADS1015 {

  /**
   * This is the primary I2C address for the ADS1015 chip when the ADDR pin is connected to GND.
   * The alternate addresses are 0x49, 0x4A and 0x4B when the ADDR pin is connected to VDD, SDA or SCL respectively.
   */
  public static final int I2C_ADDRESS = 0x48;

  /**
   * The ADS1015 contains two 16 bit registers:
   *   - 0b00 is the conversion register where data is stored after an ADC operation
   *   - 0b01 is the configuration register
   */
  public static final int CONVERSION_REGISTER = 0b00;
  public static final int CONFIG_REGISTER = 0b01;


  /**
   * The ADS1015 has a conversion delay of 1ms.
   * We can afford to wait for a little longer and be sure the conversion has finished.
   */
  public static final int CONVERSION_DELAY = 100;

  /**
   * These are the LSB of the 16 bit config register and represent:
   *   - data rate of 128 samples per second (bits 7 to 5)
   *   - traditional comparator mode with hysteresis (bit 4)
   *   - comparator polarity with active low (bit 3)
   *   - non latching comparator (bit 2)
   *   - disable comparator (bits 1 and 0)
   */
  private static final byte DEFAULT_OPTIONS = (byte) 0b00000011;

  /**
   * Write 1 to bit 15 of the config register to start a conversion.
   */
  private static final byte BEGIN_SINGLE_CONVERSION = (byte) 0b10000000;

  private I2CBus bus;
  private I2CDevice device;

  private Gain gain = Gain.TWO_THIRDS;
  private Lock lock;

  public ADS1015( Lock lock ) throws I2CFactory.UnsupportedBusNumberException, DeviceNotFoundException {
    this( lock, I2C_ADDRESS );
  }

  public ADS1015( Lock lock, int address ) throws I2CFactory.UnsupportedBusNumberException, DeviceNotFoundException {
      this.lock = lock;

      try {
        lock.lock();

        bus = I2CFactory.getInstance( I2CBus.BUS_1 );
        device = bus.getDevice( address );
      } catch ( IOException e ) {
        throw new DeviceNotFoundException( String.format( "ADS1015 sensor not found at address 0x%s.", Integer.toHexString( address ) ), e );
      } finally {
        lock.unlock();
      }
  }

  public double readSingleEnded( Channel channel ) throws InterruptedException {
    return readSingleEnded( channel, gain );
  }

  public double readSingleEnded( Channel channel, Gain gain ) throws InterruptedException {
    try {
      lock.lock();

      // reading a value from a single ended input will always read a positive value
      // however, because of device offset, the ADS1015 can still output negative values
      double value = readVoltage( channel, gain );
      return value < 0.0 ? 0.0 : value;
    } finally {
      lock.unlock();
    }
  }

  public double readDifferential( Channel channel ) throws InterruptedException {
    return readDifferential( channel, gain );
  }

  public double readDifferential( Channel channel, Gain gain ) throws InterruptedException {
    try {
      lock.lock();

      return readVoltage( channel, gain );
    } finally {
      lock.unlock();
    }
  }

  private synchronized double readVoltage( Channel channel, Gain gain ) throws InterruptedException {
    byte options = (byte) ( BEGIN_SINGLE_CONVERSION | channel.getValue() | gain.getGain() | Mode.SINGLE.getValue() );

    try {
      device.write( CONFIG_REGISTER, new byte[] { options, DEFAULT_OPTIONS }, 0, 2 ); // write two bytes
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    Thread.sleep( CONVERSION_DELAY ); // wait for the conversion to complete

    byte[] data = new byte[2];

    try {
      device.read( CONVERSION_REGISTER, data, 0, data.length );
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    int value = ( data[0] & 0xFF ) << 4 | ( data[1] & 0xFF ) >> 4;

    if( ( value & 0x800 ) != 0 ) { // check for sign bit (this is a two's complement)
      value -= 1 << 12;
    }

    return value / 2048.0 * gain.getFullScale(); // 11 bits value since the first bit is the sign bit
  }

  public void setGain( Gain gain ) {
    this.gain = gain;
  }

  public Gain getGain() {
    return gain;
  }
}
