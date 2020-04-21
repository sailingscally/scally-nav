package org.scally.spikes;

import org.scally.server.core.sensors.env.BME280;
import org.scally.server.core.sensors.env.BME280Data;
import org.scally.server.core.sensors.adc.ADS1015;
import org.scally.server.core.sensors.adc.Channel;
import org.scally.server.core.sensors.adc.Gain;

public class Loop {

  public static void main( String[] args ) throws Exception {
    ADS1015 ads = new ADS1015();
    ads.setGain( Gain.ONE );

    BME280 bme280 = new BME280();

    while( true ) {
      double v0 = ads.readSingleEnded( Channel.ZERO );
      double v1 = ads.readSingleEnded( Channel.ONE );
      double v2 = ads.readSingleEnded( Channel.TWO );
      double v3 = ads.readSingleEnded( Channel.THREE );

      double v01 = ads.readDifferential( Channel.ZERO_ONE );
      double v23 = ads.readDifferential( Channel.TWO_THREE );

      System.out.format( ">> v0: %.2f\n", v0 );
      System.out.format( ">> v1: %.2f\n", v1 );
      System.out.format( ">> v2: %.2f\n", v2 );
      System.out.format( ">> v3: %.2f\n", v3 );
      System.out.format( ">> v01: %.2f\n", v01 );
      System.out.format( ">> v23: %.2f\n", v23 );
      System.out.println();

      BME280Data data = bme280.read();

      System.out.printf( "Temperature in Celsius : %.1f C %n", data.getTemperature() );
      System.out.printf( "Pressure : %.2f hPa %n", data.getPressure() );
      System.out.printf( "Relative Humidity : %.1f %% RH %n", data.getHumidity() );
      System.out.println();

      Thread.sleep( 5000 );
    }
  }
}
