package org.scally.spikes;

import org.scally.server.core.gps.GpsAntenna;
import org.scally.server.core.serial.SerialInterface;
import org.scally.server.core.serial.SerialLineReader;

public class Location {

  public static void main( String[] args ) throws Exception {
    GpsAntenna gps = new GpsAntenna();

    SerialLineReader reader = new SerialLineReader();
    reader.addProcessor( gps );

    SerialInterface serial = new SerialInterface();
    serial.addReader( reader );

    while( true ) {
      Thread.sleep( 1000 );

      try {
        System.out.format("UTC: %s | %s | %s\n", gps.getTimeInUTC(), gps.getFixStatus(), gps.getPosition() != null ? gps.getPosition().toFormatedString() : "" );
        System.out.format( "SOG: %.1f | COG: %.0f | V: %.1f | Satellites in View: %d | PRNs: %d\n",
          gps.getSpeedOverGround(), gps.getCourseOverGround(), gps.getVariation(),
          gps.getSatellitesInView() != null ? gps.getSatellitesInView().size() : 0, gps.getPRNs() != null ? gps.getPRNs().size() : 0 );
        System.out.println();
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
  }
}
