package org.scally.server.core.gps;

import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.powermock.reflect.Whitebox.setInternalState;

public class GpsAntennaTest {

  @Test
  public void processData() {
    GpsAntenna gps = mock( GpsAntenna.class );
    doCallRealMethod().when( gps ).processLine( anyString() );
    doCallRealMethod().when( gps ).processRMC( any( String[].class ) );
    doCallRealMethod().when( gps ).processGSA( any( String[].class ) );
    doCallRealMethod().when( gps ).processGSV( any( String[].class ) );
    doCallRealMethod().when( gps ).getPosition();
    doCallRealMethod().when( gps ).getTimeInUTC();
    doCallRealMethod().when( gps ).getSpeedOverGround();
    doCallRealMethod().when( gps ).getCourseOverGround();
    doCallRealMethod().when( gps ).getVariation();

    doCallRealMethod().when( gps ).getFixStatus();
    doCallRealMethod().when( gps ).getPRNs();

    doCallRealMethod().when( gps ).getSatellitesInView();

    setInternalState( gps, "utc", Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) ) );
    setInternalState( gps, "gsv", 1 );

    gps.processLine( "$GPRMC,130039,A,3841.9706,N,00911.5075,W,4.578,245.7,230420,1.8,W,A*15" );
    gps.processLine( "$GPGSA,A,2,18,25,29,,,,,,,,,,23.4,12.2,20.0*00" );
    gps.processLine( "$GPGSV,3,1,9,29,61,042,44,16,61,314,,06,42,240,,21,38,151,*45" );
    gps.processLine( "$GPGSV,3,2,9,18,38,138,42,26,37,296,,15,35,116,,25,34,080,41*4B" );
    gps.processLine( "$GPGSV,3,3,9,51,00,000,00*74" );

    assertEquals( "38.69951000, -9.19179167", gps.getPosition().toString() );
    assertEquals( "2020-04-23T13:00:39Z", gps.getTimeInUTC().toString() );
    assertEquals( 4.578, gps.getSpeedOverGround(), 0.0 );
    assertEquals( 245.7, gps.getCourseOverGround(), 0.0 );
    assertEquals( -1.8, gps.getVariation(), 0.0 );

    assertEquals( GpsFix.FIX_2D, gps.getFixStatus() );
    assertEquals( Arrays.asList( 18, 25, 29 ), gps.getPRNs() );

    Map<Integer, SatelliteInfo> satellites = new HashMap<>();

    satellites.put( 29, new SatelliteInfo( "29", "61", "042", "44" ) );
    satellites.put( 16, new SatelliteInfo( "16", "61", "314", "") );
    satellites.put( 6, new SatelliteInfo( "06", "42", "240", "") );
    satellites.put( 21, new SatelliteInfo( "21", "38", "151", "") );
    satellites.put( 18, new SatelliteInfo( "18", "38", "138", "42") );
    satellites.put( 26, new SatelliteInfo( "26", "37", "296", "") );
    satellites.put( 15, new SatelliteInfo( "15", "35", "116", "") );
    satellites.put( 25, new SatelliteInfo( "25", "34", "080", "41") );
    satellites.put( 51, new SatelliteInfo( "51", "00", "000", "00") );

    assertEquals( 9, gps.getSatellitesInView().size() );

    for( int prn : satellites.keySet() ) {
      assertEquals( satellites.get( prn ).toString(), gps.getSatellitesInView().get( prn ).toString() );
    }
  }
}
