package org.scally.server.core.gps;

public class SatelliteInfo {

  private int prn;
  private int elevation;
  private int azimuth;
  private int snr;

  public SatelliteInfo( String prn, String elevation, String azimuth, String snr ) {
    this( prn.length() != 0 ? Integer.parseInt( prn ) : 0, elevation.length() != 0 ? Integer.parseInt( elevation ) : 0,
      azimuth.length() != 0 ? Integer.parseInt( azimuth ) : 0, snr.length() != 0 ? Integer.parseInt( snr ) : 0 );
  }

  public SatelliteInfo( int prn, int elevation, int azimuth, int snr ) {
    this.prn = prn;
    this.elevation = elevation;
    this.azimuth = azimuth;
    this.snr = snr;
  }

  public int getPRN() {
    return prn;
  }

  public int getElevation() {
    return elevation;
  }

  public int getAzimuth() {
    return azimuth;
  }

  public int getSNR() {
    return snr;
  }

  @Override
  public String toString() {
    return String.format( "PRN: %d, Elevation: %d, Azimuth: %d, SNR: %d", prn, elevation, azimuth, snr );
  }
}
