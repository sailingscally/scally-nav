package org.scally.server.core.gps;

import com.google.common.annotations.VisibleForTesting;
import org.scally.server.core.serial.SerialInterface;
import org.scally.server.core.serial.SerialLineProcessor;
import org.scally.server.core.serial.SerialReader;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Receives and parses GPS data from a GPS antenna connected to a serial port communicating via NMEA 0183
 *
 * The GPS antenna used is a Raymarine unit, connected to the Raspberry PI via a 6N137 optocoupler.
 * See schematic here: http://www.n1ewb.com/Projects/ProjectFiles/NMEARepeaterRevA.pdf
 *
 * References for NMEA 0183:
 *   - http://www.aeroelectric.com/Reference_Docs/NMEA/NMEA.pdf
 *   - http://aprs.gids.nl/nmea/
 */
public class GpsAntenna implements SerialLineProcessor {

  public static final String RMB = "$GPRMB"; // Recommended Minimum Navigation Information
  public static final String RMC = "$GPRMC"; // Recommended Minimum Specific GPS/TRANSIT Data
  public static final String GGA = "$GPGGA"; // Global Positioning System Fix Data
  public static final String VTG = "$GPVTG"; // Actual track made good and speed over ground
  public static final String RMA = "$GPRMA"; // Navigation data from present position
  public static final String GSA = "$GPGSA"; // GPS receiver operating mode, SVs used for navigation, and DOP values.
  public static final String GSV = "$GPGSV"; // Number of SVs in view, PRN numbers, elevation, azimuth & SNR values.

  private GpsFix fix = GpsFix.NONE;
  private GpsPosition position;

  private double speed;
  private double track;
  private double variation;

  private SerialInterface serial;

  private int gsv = 1;
  private List<Integer> prns;
  private Map<Integer, SatelliteInfo> satellites;
  private Calendar utc = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );

  public GpsAntenna() throws IOException {
    serial = new SerialInterface();
    serial.start( new SerialReader( this ) );
  }

  public void shutdown() throws IOException {
    serial.shutdown();
  }

  @Override
  public void processLine( String line ) {
    if( !line.startsWith( "$" ) ) {
      // this isn't a valid NMEA 0183 sentence, just ignore it
      return;
    }

    // remove the checksum from the NMEA sentence
    String[] data = line.substring( 0, line.lastIndexOf( '*' ) ).split( ",", -1 );

    switch ( data[0] ) {
      case RMC:
        processRMC( data );
        break;

      case GSA:
        processGSA( data );
        break;

      case GSV:
        processGSV( data );
        break;
    }
  }

  @VisibleForTesting
  void processRMC( String[] data ) {
    // sample RMC sentence with no GPS signal
    // $GPRMC,001205,V,,,,,,,100803,,,N*5F
    // sample RMC sentence with a GPS fix
    // $GPRMC,130039,A,3841.9706,N,00911.5075,W,0.000,000.0,230420,1.8,W,A*15

    if( !data[2].equals( "A" ) ) {
      // no GPS position fix
      fix = GpsFix.NONE;
      return;
    }

    utc.set( Integer.parseInt( data[9].substring( 4, 6 ) ) + 2000, // year
      Integer.parseInt( data[9].substring( 2, 4 ) ) - 1,         // month (zero based)
      Integer.parseInt( data[9].substring( 0, 2 ) ),                    // day
      Integer.parseInt( data[1].substring( 0, 2 ) ),                    // hours
      Integer.parseInt( data[1].substring( 2, 4 ) ),                    // minutes
      Integer.parseInt( data[1].substring( 4, 6 ) ) );                  // seconds
    utc.clear( Calendar.MILLISECOND );

    position = new GpsPosition( data[3], data[4], data[5], data[6] );
    speed = Double.parseDouble( data[7] );
    track = Double.parseDouble( data[8] );
    variation = Double.parseDouble( data[10] ) * ( data[11].equals( "E" ) ? 1.0 : -1.0 );
  }

  @VisibleForTesting
  void processGSA( String[] data ) {
    // sample GSA sentence with no GPS signal
    // $GPGSA,A,1,,,,,,,,,,,,,50.0,20.0,50.0*02
    // sample GSA sentence with a GPS fix
    // $GPGSA,A,2,18,25,29,,,,,,,,,,23.4,12.2,20.0*00

    if( data[2].equals( "1" ) ) {
      // no GPS position fix
      fix = GpsFix.NONE;
      return;
    }

    fix = data[2].equals( "3" ) ? GpsFix.FIX_3D : GpsFix.FIX_2D;
    prns = new ArrayList<>();

    // satellite PRNs used for the current fix (positions 3 to 14, empty for unused fields)
    for( int i = 3; i <= 14; i ++ ) {
      if( data[i].length() == 0 ) {
        break;
      }

      prns.add( Integer.parseInt( data[i] ) );
    }
  }

  /**
   * Gathers information about the satellites in view.
   */
  @VisibleForTesting
  void processGSV( String[] data ) {
    // sample GSV sentence sequence
    // $GPGSV,3,1,9,29,61,042,44,16,61,314,,06,42,240,,21,38,151,*45
    // $GPGSV,3,2,9,18,38,138,42,26,37,296,,15,35,116,,25,34,080,41*4B
    // $GPGSV,3,3,9,51,00,000,00*74

    if( Integer.parseInt( data[2] ) != gsv ) {
      // this is the incorrect message number for this cycle
      return;
    }

    int view = Integer.parseInt( data[3] ); // number of satellites in view

    if( gsv == 1 ) {
      // prepare for a new cycle
      satellites = new HashMap<>( view );
    }

    // number of satellites to find in this message
    // each message may contain up to 4 satellites, last message may contain less
    int find = view >= gsv * 4 ? 4 : view % 4;

    for( int i = 0; i < find; i ++ ) {
      // start at position 4, each satellite will have 4 positions
      satellites.put( Integer.parseInt( data[4 + i * 4] ),
        new SatelliteInfo( data[4 + i * 4], data[5 + i * 4], data[6 + i * 4], data[7 + i * 4] ) );
    }

    gsv ++; // prepare for next message

    if( data[1].equals( data[2] ) ) {
      // end of cycle, prepare for next cycle
      gsv = 1;
    }
  }

  public GpsPosition getPosition() {
    return position;
  }

  public GpsFix getFixStatus() {
    return fix;
  }

  public Instant getTimeInUTC() {
    return utc.toInstant();
  }

  public double getSpeedOverGround() {
    return speed;
  }

  public double getCourseOverGround() {
    return track;
  }

  public double getVariation() {
    return variation;
  }

  public List<Integer> getPRNs() {
    return prns;
  }

  public Map<Integer, SatelliteInfo> getSatellitesInView() {
    return satellites;
  }
}
