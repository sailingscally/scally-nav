package org.scally.server.core.sensors;

public class BME280Data {

    private double t_fine; // internal use only!
    private double temperature, pressure, humidity;

    public BME280Data( byte[] data, BME280Calibration calibration ) {
      setTemperature( data, calibration );
      setPressure( data, calibration );
      setHumidity( data, calibration );
    }

    public void setTemperature( byte[] data, BME280Calibration calibration ) {


      long adc_t =
        ( ( (long) ( data[ 3 ] & 0xFF ) * 65536 ) + ( (long) ( data[ 4 ] & 0xFF ) * 256 ) + (long) ( data[ 5 ] & 0xF0 ) )
          / 16;

      System.out.printf( "      >> adc_t : %d %n", adc_t );


      double var1 = ( ( (double) adc_t ) / 16384.0 - ( (double) calibration.T1 ) / 1024.0 ) * ( (double) calibration.T2 );
      double var2 = ( ( ( (double) adc_t ) / 131072.0 - ( (double) calibration.T1 ) / 8192.0 ) *
        ( ( (double) adc_t ) / 131072.0 - ( (double) calibration.T1 ) / 8192.0 ) ) * ( (double) calibration.T3 );
      double t_fine = (long) ( var1 + var2 );
      double cTemp = ( var1 + var2 ) / 5120.0;
      double fTemp = cTemp * 1.8 + 32;

      System.out.printf( "Temperature in Celsius : %.2f C %n", cTemp );
      System.out.printf( "Temperature in Fahrenheit : %.2f F %n", fTemp );



      long value = ( ( data[3] & 0xFF ) << 12 ) + ( ( data[4] & 0xFF ) << 4) + ( ( data[5] & 0xF0 ) >> 4 );

      System.out.format( "## Uncalibrated temperature value: %d\n", value );
      System.out.format( "## Uncalibrated temperature value: %d (1/16)\n", value / 16 );

      double var1 = ( value / 16384.0 - calibration.T1 / 1024.0 ) * calibration.T2;
      double var2 = ( ( value / 131072.0 - calibration.T1 / 8192.0 ) * ( value / 131072.0 - calibration.T1/8192.0 ) )  * calibration.T3;

      t_fine = var1 + var2;
      temperature = t_fine / 5120.0;

      System.out.format( "## Temperature value: %.2f\n", temperature );
    }

    public void setPressure( byte[] data, BME280Calibration calibration ) {

    }

    public void setHumidity( byte[] data, BME280Calibration calibration ) {

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
