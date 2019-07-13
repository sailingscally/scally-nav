package org.scally.server.core.sensors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

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
   *   - 0x56/0x57 for sample chips
   *   - 0x58 for mass production chips
   */
  public static final int CHIP_ID_ADDRESS = 0xD0;
  public static final int CHIP_ID_VALUE = 0x60;

  /**
   * Writing the value 0xB6 to the reset register at address 0xE0 will perform a soft reset of the BME 280 chip.
   * The value read from this address will always be 0x00 but there is no need to read data from this register.
   */
  public static final int RESET_ADDRESS = 0xE0;
  public static final int RESET_VALUE = 0xB6;

  /**
   * The status register contains two bits which indicate the status of the device
   *   - bit 0 - automatically set to 1 when the NVM data is being copied to the registers and back to 0 when done
   *             data is copied at power-on-reset and after each conversion
   *   - bit 3 - automatically set to 1 when a conversion is running and back to 0 once the results have been stored
   */
  public static final int STATUS_ADDRESS = 0xF3;
  public static final int STATUS_UPDATING_MASK = 0x01; // mask for bit 0
  public static final int STATUS_MEASURING_MASK = 0x04; // mask for bit 3

  /**
   * Calibration data for temperature, pressure and humidity is found in two memory banks:
   *   - the first bank starts at address 0x88 and finishes at 0xA1 taking 25 bytes of data
   *   - the second bank goes from 0xE1 to 0xE7 taking 7 bytes of data
   *
   * There is a total of 32 bytes of calibration data:
   *   - 6 bytes for temperature
   *   - 18 bytes for pressure
   *   - 8 bytes for humidity
   *
   * From this data, the various calibration parameters can be calculated.
   * There are 3 calibration parameters for temperature, 9 for pressure and 6 for humidity.
   */
  public static final int CALIBRATION_BANK1_START_ADDRESS = 0x88;
  public static final int CALIBRATION_BANK2_START_ADDRESS = 0xE1;
  public static final int CALIBRATION_BANK1_LENGTH = 25;
  public static final int CALIBRATION_BANK2_LENGTH = 7;

  private I2CBus bus;
  private I2CDevice device;

  private BME280Calibration calibration = new BME280Calibration();

  public BME280( /* I2C */ ) {
    // TODO: store I2C wrapper for thread safe usage

    try {
      bus = I2CFactory.getInstance( I2CBus.BUS_1 );
      device = bus.getDevice( I2C_ADDRESS );
    } catch ( I2CFactory.UnsupportedBusNumberException e ) {
      e.printStackTrace();
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }

  public void init() {
// After
    //setting the measurement and filter options and enabling normal mode, the last measurement
    //results can always be obtained at the data registers without the need of further write accesses.

    // The BME280 measurement period consists of a temperature, pressure and humidity
    //measurement with selectable oversampling.



    // use settings from: 3.5.1 Weather monitoring
  }

  public void calibrate() {
    byte[] bank1 = new byte[ CALIBRATION_BANK1_LENGTH ];
    byte[] bank2 = new byte[ CALIBRATION_BANK2_LENGTH ];

    try {
      // read 25 bytes of data from the first bank of calibration data
      device.read(CALIBRATION_BANK1_START_ADDRESS, bank1, 0, CALIBRATION_BANK1_LENGTH);

      // read 7 bytes of data from the second bank of calibration data
      device.read(CALIBRATION_BANK2_START_ADDRESS, bank2, 0, CALIBRATION_BANK2_LENGTH);
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    for( int i = 0; i < bank1.length; i ++ ) {
      System.out.format( "Calibration byte %d: %d [%s]\n", i, bank1[i], Integer.toHexString ( bank1[i] ) );
    }

    for( int i = 0; i < bank2.length; i ++ ) {
      System.out.format( "Calibration byte %d: %d [%s]\n", i + bank1.length, bank2[i], Integer.toHexString( bank2[i] ) );
    }

    // calculate temperature calibration parameters
    calibration.T1 = ( ( bank1[1] & 0xFF ) << 8 ) + ( bank1[0] & 0xFF ); // unsigned
    calibration.T2 = ( ( ( ( bank1[3] & 0xFF ) << 8 ) + ( bank1[2] & 0xFF ) ) << 1 ) >> 1; // signed
    calibration.T3 = ( ( ( ( bank1[5] & 0xFF ) << 8 ) + ( bank1[4] & 0xFF ) ) << 1 ) >> 1; // signed

    System.out.format( "---->>>> T1: %d\n", calibration.T1 );
    System.out.format( "---->>>> T2: %d\n", calibration.T2 );
    System.out.format( "---->>>> T3: %d\n", calibration.T3 );

    // The BME280 output consists of the ADC output values. However, each sensing element
    //behaves differently. Therefore, the actual pressure and temperature must be calculated using a
    //set of calibration parameters.

    // The recommended calculation uses fixed point arithmetic and is given in chapter 4.2.3.
    // In high-level languages like Matlab™ or LabVIEW™, fixed-point code may not be well
    // supported. In this case the floating-point code in appendix 8.1 can be used as an alternative.

    // Both pressure and temperature values are expected to be received in 20 bit format,
    // positive, stored in a 32 bit signed integer. Humidity is expected to be received in 16 bit format,
    // positive, stored in a 32 bit signed integer.
  }

  public void read() {
    // To read out data after a conversion, it is strongly recommended to use a burst read and not
    // address every register individually. This will prevent a possible mix-up of bytes belonging to
    // different measurements and reduce interface traffic.

    // Data readout is done by starting a burst read from 0xF7 to 0xFC (temperature and pressure) or
    // from 0xF7 to 0xFE (temperature, pressure and humidity). The data are read out in an unsigned
    // 20-bit format both for pressure and for temperature and in an unsigned 16-bit format for humidity.
  }



  public int t() {
    // Temperature measurement can be enabled or skipped. Skipping the measurement could be
    // useful to measure pressure extremely rapidly. When enabled, several oversampling options
    // exist. The temperature measurement is controlled by the osrs_t[2:0] setting which is detailed in
    // chapter 5.4.5. For the temperature measurement, oversampling is possible to reduce the noise.
    // The resolution of the temperature data depends on the IIR filter (see chapter 0) and the
    // oversampling setting (see chapter 5.4.5):
    //   - When the IIR filter is enabled, the temperature resolution is 20 bit.
    //   - When the IIR filter is disabled, the temperature resolution is 16 + (osrs_t – 1) bit, e.g. 18
    //     bit when osrs_t is set to ‘3’.

    return 0;
  }

  public int getPressure() {
    // Pressure measurement can be enabled or skipped. When enabled, several oversampling
    // options exist. The pressure measurement is controlled by the osrs_p[2:0] setting which is
    // detailed in chapter 5.4.5. For the pressure measurement, oversampling is possible to reduce
    // the noise. The resolution of the pressure data depends on the IIR filter (see chapter 0) and the
    // oversampling setting (see chapter 5.4.5):
    //   - When the IIR filter is enabled, the pressure resolution is 20 bit.
    //   - When the IIR filter is disabled, the pressure resolution is 16 + (osrs_p – 1) bit, e.g. 18 bit
    //     when osrs_p is set to ‘3’.

    return 0;
  }

  public int getHumidity() {
    // The humidity measurement can be enabled or skipped. When enabled, several oversampling
    // options exist. The humidity measurement is controlled by the osrs_h[2:0] setting, which is
    // detailed in chapter 5.4.3. For the humidity measurement, oversampling is possible to reduce the
    // noise. The resolution of the humidity measurement is fixed at 16 bit ADC output.

    return 0;
  }
}
