package org.scally.spikes;

import org.scally.server.core.gps.GpsAntenna;
import org.scally.server.core.gps.GpsFix;
import org.scally.server.core.gps.SatelliteInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Location {

  public static void main( String[] args ) throws Exception {
    GpsAntenna gps = new GpsAntenna();

    while( true ) {
      Thread.sleep( 1000 );

      try {
        System.out.format("UTC: %s | %s | %s\n", gps.getTimeInUTC(), gps.getFixStatus(), gps.getPosition() != null ? gps.getPosition().toFormatedString() : "" );
        System.out.format( "SOG: %.1f | COG: %.1f | V: %.1f | Satellites in View: %d | PRNs: %d\n",
          gps.getSpeedOverGround(), gps.getCourseOverGround(), gps.getVariation(),
          gps.getSatellitesInView() != null ? gps.getSatellitesInView().size() : 0, gps.getPRNs() != null ? gps.getPRNs().size() : 0 );
        System.out.println();
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
  }
}
