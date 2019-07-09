package org.scally.server.core.sensors.env;

public class BME280Data {

  private double t_fine; // internal use only!
  private double temperature, pressure, humidity;

  public BME280Data( byte[] data, BME280Calibration calibration ) {
    setTemperature( data, calibration );
    setPressure( data, calibration );
    setHumidity( data, calibration );
  }

  private void setTemperature( byte[] data, BME280Calibration calibration ) {
    long value = ( ( data[ 3 ] & 0xFF ) << 12 ) + ( ( data[ 4 ] & 0xFF ) << 4 ) + ( ( data[ 5 ] & 0xF0 ) >> 4 );

    double v1 = ( value / 16384.0 - calibration.T1 / 1024.0 ) * calibration.T2;
    double v2 = ( ( value / 131072.0 - calibration.T1 / 8192.0 ) * ( value / 131072.0 - calibration.T1 / 8192.0 ) ) * calibration.T3;

    t_fine = v1 + v2;
    temperature = t_fine / 5120.0;
  }

  private void setPressure( byte[] data, BME280Calibration calibration ) {
    long value = ( ( data[ 0 ] & 0xFF ) << 12 ) + ( ( data[ 1 ] & 0xFF ) << 4 ) + ( ( data[ 2 ] & 0xF0 ) >> 4 );

    double v1 = t_fine / 2.0 - 64000.0;
    double v2 = v1 * v1 * calibration.P6 / 32768.0;
    v2 = v2 + v1 * calibration.P5 * 2.0;
    v2 = ( v2 / 4.0 ) + ( calibration.P4 * 65536.0 );
    v1 = ( calibration.P3 * v1 * v1 / 524288.0 + calibration.P2 * v1 ) / 524288.0;
    v1 = ( 1.0 + v1 / 32768.0 ) * calibration.P1;

    if ( v1 == 0.0 ) {
      pressure = 0;
      return; // avoid exception caused by division by zero
    }

    pressure = 1048576.0 - value;

    pressure = ( pressure - ( v2 / 4096.0 ) ) * 6250.0 / v1;
    v1 = calibration.P9 * pressure * pressure / 2147483648.0;
    v2 = pressure * calibration.P8 / 32768.0;
    pressure = pressure + ( v1 + v2 + calibration.P7 ) / 16.0;

    pressure = pressure / 100.0;
  }

  private void setHumidity( byte[] data, BME280Calibration calibration ) {
    long value = ( ( data[ 6 ] & 0xFF ) << 8 ) + ( data[ 7 ] & 0xFF );

    humidity = t_fine - 76800.0;

    humidity = ( value - ( calibration.H4 * 64.0 + calibration.H5 / 16384.0 * humidity ) )
      * ( calibration.H2 / 65536.0 * ( 1.0 + calibration.H6 / 67108864.0 * humidity * ( 1.0
      + calibration.H3 / 67108864.0 * humidity ) ) );
    humidity = humidity * ( 1.0 - calibration.H1 * humidity / 524288.0 );

    if ( humidity > 100.0 ) {
      humidity = 100.0;
    } else if ( humidity < 0.0 ) {
      humidity = 0.0;
    }
  }

  public double getTemperature() {
    return temperature;
  }

  public double getPressure() {
    return pressure;
  }

  public double getHumidity() {
    return humidity;
  }
}
