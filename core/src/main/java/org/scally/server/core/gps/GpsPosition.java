package org.scally.server.core.gps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GpsPosition {

  public static final String LATITUDE_FORMAT = "%02dº %05.2f' %s";
  public static final String LONGITUDE_FORMAT = "%03dº %05.2f' %s";

  @JsonProperty( "lat" )
  private double lat;

  @JsonProperty( "lng" )
  private double lng;

  /**
   *
   *
   * @param ns N for North, S for South
   * @param ew E for East, W for West
   */
  public GpsPosition( String lat, String ns, String lng, String ew ) {
    // sample: 3841.9706,N,00911.5075,W = 38 degrees 41.9706 minutes North, 9 degrees 11.5075 W
    this.lat = Double.parseDouble( lat.substring( 0, 2 ) ) + Double.parseDouble( lat.substring( 2 ) ) / 60.0;
    this.lng = Double.parseDouble( lng.substring( 0, 3 ) ) + Double.parseDouble( lng.substring( 3 ) ) / 60.0;

    this.lat *= ns.equals( "N" ) ? 1.0 : -1.0;
    this.lng *= ew.equals( "E" ) ? 1.0 : -1.0;
  }

  @Override
  public String toString() {
    return String.format( "%.8f, %.8f", lat, lng );
  }

  public String toFormatedString() {
    return String.format( "%s, %s", getLatitude( LATITUDE_FORMAT ), getLongitude( LONGITUDE_FORMAT ) );
  }

  public double getLatitude() {
    return lat;
  }

  public String getLatitude( String format ) {
    int degrees = (int) Math.floor( Math.abs( lat ) );
    double minutes = ( Math.abs( lat ) - degrees ) * 60;

    return String.format( format, degrees, minutes, lat < 0 ? "S" : "N" );
  }

  public double getLongitude() {
    return lng;
  }

  public String getLongitude( String format ) {
    int degrees = (int) Math.floor( Math.abs( lng ) );
    double minutes = ( Math.abs( lng ) - degrees ) * 60;

    return String.format( format, degrees, minutes, lng < 0 ? "W" : "E" );
  }
}
