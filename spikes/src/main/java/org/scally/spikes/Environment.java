package org.scally.spikes;

import org.scally.server.core.sensors.env.BME280;
import org.scally.server.core.sensors.env.BME280Data;

import java.util.concurrent.locks.ReentrantLock;

public class Environment {

  public static void main( String args[] ) throws Exception {
    BME280 bme280 = new BME280( new ReentrantLock() );

    while( true ) {
      BME280Data data = bme280.read();

      System.out.printf( "Temperature in Celsius : %.1f C %n", data.getTemperature() );
      System.out.printf( "Pressure : %.2f hPa %n", data.getPressure() );
      System.out.printf( "Relative Humidity : %.1f %% RH %n", data.getHumidity() );
      System.out.println();

      Thread.sleep( 10000 );
    }
  }
}
