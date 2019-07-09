package org.scally.server.core.gps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GpsPositionTest {

  @Test
  public void parse() {
    GpsPosition position = new GpsPosition( "3841.9706", "N", "00911.5075", "W");
    assertEquals( "38.69951000, -9.19179167", position.toString() );
  }

  @Test
  public void latitude() {
    GpsPosition position = new GpsPosition( "3841.9706", "N", "00911.5075", "W");
    assertEquals( 38.69951000, position.getLatitude(), 0.0 );
  }

  @Test
  public void longitude() {
    GpsPosition position = new GpsPosition( "3841.9706", "N", "00911.5075", "W");
    assertEquals( -9.19179167, position.getLongitude(), 0.00000001 );
  }

  @Test
  public void format() {
    GpsPosition position = new GpsPosition( "3841.9706", "N", "00911.5075", "W");
    assertEquals( "38º 41.97' N, 009º 11.51' W", position.toFormatedString() );
  }
}
