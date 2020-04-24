package org.scally.spikes;

import org.scally.server.core.sensors.adc.ADS1015;
import org.scally.server.core.sensors.adc.Channel;
import org.scally.server.core.sensors.adc.Gain;

import java.util.concurrent.locks.ReentrantLock;

public class Voltages {
  public static void main( String[] args ) throws Exception {
    ADS1015 ads = new ADS1015( new ReentrantLock() );
    ads.setGain( Gain.ONE );

    while( true ) {
      double v0 = ads.readSingleEnded( Channel.ZERO );
      double v1 = ads.readSingleEnded( Channel.ONE );
      double v2 = ads.readSingleEnded( Channel.TWO );
      double v3 = ads.readSingleEnded( Channel.THREE );

      double v01 = ads.readSingleEnded( Channel.ZERO_ONE );
      double v23 = ads.readSingleEnded( Channel.TWO_THREE );

      System.out.format( ">> v0: %.2f\n", v0 );
      System.out.format( ">> v1: %.2f\n", v1 );
      System.out.format( ">> v2: %.2f\n", v2 );
      System.out.format( ">> v3: %.2f\n", v3 );
      System.out.format( ">> v01: %.2f\n", v01 );
      System.out.format( ">> v23: %.2f\n", v23 );
      System.out.println();

      Thread.sleep( 10000 );
    }
  }
}
