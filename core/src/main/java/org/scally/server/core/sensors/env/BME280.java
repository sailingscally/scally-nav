package org.scally.server.core.sensors.env;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.scally.server.core.sensors.DeviceNotFoundException;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

/**
 * Datasheet can be found at: https://www.embeddedadventures.com/datasheets/BME280.pdf
 *
 * Portions of this have been ported from:
 *   - https://github.com/BoschSensortec/BME280_driver/blob/master/bme280.c
 *   - https://github.com/adafruit/Adafruit_BME280_Library/blob/master/Adafruit_BME280.cpp
 */
public class BME280 {

  /**
   * This is the primary I2C address for the Bosch BME 280 chip.
   * The alternate address is 0x76 which is found on some chips.
   */
  public static final int I2C_ADDRESS = 0x77;

  /**
   * The BME 280 chip id can be read from the address 0xD0 and should have a value of 0x60.
   * If the value is different, other device may be using the I2C address where the BME 280 was expected to be found.
   *
   * Other possible valid values are:
   *   - 0x56 or 0x57 for sample chips
   *   - 0x58 for mass production chips
   */
  public static final int CHIP_ID_ADDRESS = 0xD0;
  public static final byte CHIP_ID_VALUE = (byte) 0x60;

  /**
   * Writing the value 0xB6 to the reset register at address 0xE0 will perform a soft reset of the BME 280 chip.
   * The value read from this address will always be 0x00 but there is no need to read data from this register.
   */
  public static final int RESET_ADDRESS = 0xE0;
  public static final byte RESET_VALUE = (byte) 0xB6;

  /**
   * Data from the BME 280 sensor is available at addresses 0xF7 to 0xFE.
   * There are a total of 8 bytes with data:
   *   - temperature: 0xFA, 0xFB and 0xFC
   *   - pressure: 0xF7, 0xF8 and 0xF9
   *   - humidity: 0xFD and 0xFE
   */
  public static final int DATA_ADDRESS = 0xF7;
  public static final int DATA_LENGTH = 8;

  /**
   * The status register contains two bits which indicate the status of the device
   *   - bit 0 - automatically set to 1 when the NVM data is being copied to the registers and back to 0 when done
   *             data is copied at power-on-reset and after each conversion
   *   - bit 3 - automatically set to 1 when a conversion is running and back to 0 once the results have been stored
   */
  public static final int STATUS_ADDRESS = 0xF3;
  public static final int STATUS_UPDATING_MASK = 0x01; // mask for bit 0
  public static final int STATUS_MEASURING_MASK = 0x08; // mask for bit 3

  /**
   * Calibration data for temperature, pressure and humidity is found in two memory banks:
   *   - the first bank starts at address 0x88 and finishes at 0xA1 taking 26 bytes of data
   *   - the second bank goes from 0xE1 to 0xE7 taking 7 bytes of data
   *
   * There is a total of 32 bytes of calibration data (address 0xA0 is not used):
   *   - 6 bytes for temperature
   *   - 18 bytes for pressure
   *   - 8 bytes for humidity
   *
   * From this data, the various calibration parameters can be calculated.
   * There are 3 calibration parameters for temperature, 9 for pressure and 6 for humidity.
   */
  public static final int CALIBRATION_BANK1_START_ADDRESS = 0x88;
  public static final int CALIBRATION_BANK2_START_ADDRESS = 0xE1;
  public static final int CALIBRATION_BANK1_LENGTH = 26;
  public static final int CALIBRATION_BANK2_LENGTH = 7;

  /**
   * The "ctrl_hum" register controls oversampling of humidity data.
   *
   * Only the first three bits are used:
   *   - 000 - skipped (output set to 0x80000)
   *   - 001 - x1
   *   - 010 - x2
   *   - 011 - x4
   *   - 100 - x8
   *   - 101 - x16
   *
   * Note: data written to this register will only take place after writing to the "ctrl_meas" register.
   */
  public static final int CONTROL_HUMIDITY_ADDRESS = 0xF2;

  /**
   * The "ctrl_meas" register controls oversampling for both temperature and pressure and the sensor mode.
   *
   * Bits 7, 6 and 5 control oversampling for temperature data (see table above).
   * Bits 4, 3 and 2 control oversampling for pressure data (see table above).
   *
   * Bits 1 and 0 control the sensor mode:
   *   - 00 - sleep mode
   *   - 01 and 10 - forced mode
   *   - 11 - normal mode
   */
  public static final int CONTROL_PRESSURE_TEMPERATURE_ADDRESS = 0xF4;

  /**
   * The "config" register sets the rate, filter and interface options of the device.
   *
   * Bits 7, 6 and 5 control the inactive duration in normal mode (see below).
   * Bits 4, 3 and 2 control the time constant of the IIR filter (values in milliseconds):
   *   - 000 - 0.5
   *   - 001 - 62.5
   *   - 010 - 125
   *   - 011 - 250
   *   - 100 - 500
   *   - 101 - 1000
   *   - 110 - 10
   *   - 111 - 20
   *
   * Bit 0 enables 3-wire SPI interface when set to 1.
   *
   * Note: writes to the config register in normal mode may be ignored but will not be ignored in sleed mode.
   */
  public static final int CONFIGURATION_ADDRESS = 0xF5;

  private I2CBus bus;
  private I2CDevice device;

  private BME280Calibration calibration = new BME280Calibration();
  private Lock lock;

  public BME280( Lock lock ) throws InterruptedException, I2CFactory.UnsupportedBusNumberException, DeviceNotFoundException {
    this.lock = lock;

    try {
      lock.lock();

      bus = I2CFactory.getInstance( I2CBus.BUS_1 );
      device = bus.getDevice( I2C_ADDRESS );
    } catch ( IOException e ) {
      throw new DeviceNotFoundException( String.format( "BME 280 sensor not found at address 0x%s.", Integer.toHexString( I2C_ADDRESS ) ), e );
    } finally {
      lock.unlock();
    }

    if( !check() ) {
      throw new DeviceNotFoundException( String.format( "BME 280 sensor with ID 0x%s not found at address 0x%s.",
        Integer.toHexString( CHIP_ID_VALUE ), Integer.toHexString( I2C_ADDRESS ) ) );
    }

    reset();
    calibrate();
    init();
  }

  public BME280Status status() {
    try {
      lock.lock();

      int status = device.read( STATUS_ADDRESS ) & 0xFF;

      // System.out.format( ">> BME 280 device status: %d [0x%s]\n", status, Integer.toHexString( status ) );

      if( ( status & STATUS_MEASURING_MASK ) == STATUS_MEASURING_MASK ) {
        return BME280Status.MEASURING;
      }

      if( ( status & STATUS_UPDATING_MASK ) == STATUS_UPDATING_MASK ) {
        return BME280Status.UPDATING;
      }

      return BME280Status.IDLE;
    } catch ( IOException e ) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

    return BME280Status.ERROR;
  }

  private boolean check() {
    try {
      lock.lock();

      return device.read( CHIP_ID_ADDRESS ) == CHIP_ID_VALUE;
    } catch ( IOException e ) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

    return false;
  }

  private void reset() throws InterruptedException {
    try {
      lock.lock();

      device.write( RESET_ADDRESS, RESET_VALUE );

      while ( status() != BME280Status.IDLE ) {
        Thread.sleep( 50 );
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  private void init() {
    try {
      lock.lock();

      // humidity oversampling = 1x (001)
      device.write( CONTROL_HUMIDITY_ADDRESS, (byte) 0b00000001 );

      // temperature oversampling = 1x (001), pressure oversampling = 1x (001), normal mode (11)
      device.write( CONTROL_PRESSURE_TEMPERATURE_ADDRESS, (byte) 0b00100111 );

      // 1 second standby time (101), 1 second IIR filter (110) and 3-wire SPI disabled
      device.write( CONFIGURATION_ADDRESS, (byte) 0b10110100 );
    } catch ( IOException e ) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  private void calibrate() {
    byte[] bank1 = new byte[ CALIBRATION_BANK1_LENGTH ];
    byte[] bank2 = new byte[ CALIBRATION_BANK2_LENGTH ];

    try {
      lock.lock();

      // read 26 bytes of data from the first bank of calibration data
      device.read(CALIBRATION_BANK1_START_ADDRESS, bank1, 0, CALIBRATION_BANK1_LENGTH);

      // read 7 bytes of data from the second bank of calibration data
      device.read(CALIBRATION_BANK2_START_ADDRESS, bank2, 0, CALIBRATION_BANK2_LENGTH);
    } catch ( IOException e ) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

    // calculate temperature calibration parameters
    calibration.T1 = convertToUnsignedShort( bank1[1], bank1[0] );
    calibration.T2 = convertToSignedShort( bank1[3], bank1[2] );
    calibration.T3 = convertToSignedShort( bank1[5], bank1[4] );

    // calculate pressure calibration parameters
    calibration.P1 = convertToUnsignedShort( bank1[7], bank1[6] );
    calibration.P2 = convertToSignedShort( bank1[9], bank1[8] );
    calibration.P3 = convertToSignedShort( bank1[11], bank1[10] );
    calibration.P4 = convertToSignedShort( bank1[13], bank1[12] );
    calibration.P5 = convertToSignedShort( bank1[15], bank1[14] );
    calibration.P6 = convertToSignedShort( bank1[17], bank1[16] );
    calibration.P7 = convertToSignedShort( bank1[19], bank1[18] );
    calibration.P8 = convertToSignedShort( bank1[21], bank1[20] );
    calibration.P9 = convertToSignedShort( bank1[23], bank1[22] );

    // calculate humidity calibration parameters
    calibration.H1 = bank1[25] & 0xFF; // unsigned char
    calibration.H2 = convertToSignedShort( bank2[1], bank2[0] );
    calibration.H3 = bank2[2] & 0xFF; // unsigned char
    calibration.H4 = convertToSignedShort( ( ( bank2[3] & 0xFF ) << 4 ) + ( bank2[4] & 0xF ) );
    calibration.H5 = convertToSignedShort( ( ( bank2[5] & 0xFF ) << 4 ) + ( ( bank2[4] & 0xFF ) >> 4 ) );
    calibration.H6 = bank2[6]; // signed char
  }

  public  BME280Data read() throws InterruptedException {
    while ( status() != BME280Status.IDLE ) {
      Thread.sleep( 100 );
    }

    byte[] data = new byte[ DATA_LENGTH ];

    try {
      lock.lock();

      device.read( DATA_ADDRESS, data, 0, DATA_LENGTH );
    } catch ( IOException e ) {
      e.printStackTrace();
      return null;
    } finally {
      lock.unlock();
    }

    return new BME280Data( data, calibration );
  }

  private int convertToUnsignedShort( byte msb, byte lsb ) {
    return ( ( msb & 0xFF ) << 8 ) + ( lsb & 0xFF );
  }

  private int convertToSignedShort( byte msb, byte lsb ) {
    return convertToSignedShort( convertToUnsignedShort( msb, lsb ) );
  }

  private int convertToSignedShort( int value ) {
    return value > 0x7FFF ? value - 0x10000 : value;
  }
}
