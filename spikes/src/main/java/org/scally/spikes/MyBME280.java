package org.scally.spikes;

import org.scally.server.core.sensors.BME280;

public class MyBME280 {

  public static void main( String args[] ) throws Exception {
    BME280 bme280 = new BME280();
    bme280.calibrate();

  }
}
