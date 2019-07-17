package org.scally.spikes;

import org.scally.server.core.sensors.adc.ADS1015;
import org.scally.server.core.sensors.adc.Channel;

public class MyADS1015 {

  public static void main( String[] args ) throws Exception {
    ADS1015 ads = new ADS1015();
    double v0 = ads.readSingleEnded( Channel.ZERO );
    double v1 = ads.readSingleEnded( Channel.ONE );
    double v2 = ads.readSingleEnded( Channel.TWO );
    double v3 = ads.readSingleEnded( Channel.THREE );

    System.out.format( ">> v0: %.2f\n", v0 );
    System.out.format( ">> v1: %.2f\n", v1 );
    System.out.format( ">> v2: %.2f\n", v2 );
    System.out.format( ">> v3: %.2f\n", v3 );
  }
}
