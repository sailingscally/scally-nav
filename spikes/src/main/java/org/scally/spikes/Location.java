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

      System.out.format("UTC: %s | %s | %.8f, %.8f\n", gps.getTimeInUTC(), gps.getFixStatus(), gps.getPosition().getLatitude(), gps.getPosition().getLongitude() );
      System.out.format( "SOG: %.1f | COG: %.1f | V: %.1f | Satellites in View: %d | PRNs: %d\n",
        gps.getSpeedOverGround(), gps.getCourseOverGround(), gps.getVariation(), gps.getSatellitesInView().size(), gps.getPRNs().size() );
      System.out.println();
    }
  }
}
