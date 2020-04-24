package org.scally.server.http;

import org.scally.server.core.gps.GpsAntenna;
import org.scally.server.core.gps.GpsFix;
import org.scally.server.core.gps.GpsPosition;
import org.scally.server.core.gps.SatelliteInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/api/gps" )
public class GpsController {

  private GpsAntenna gps;

  @GetMapping( path = "/status" )
  public GpsFix getStatus() {
    return gps != null ? gps.getFixStatus() : null;
  }

  @GetMapping( path = "/position" )
  public GpsPosition getPosition() {
    return gps != null ? gps.getPosition() : null;
  }

  @GetMapping( path = "/time" )
  public Instant getTime() {
    return gps != null && gps.getTimeInUTC().toEpochMilli() != 0 ? gps.getTimeInUTC() : null;
  }

  @GetMapping( path = "/sog" )
  public double getSpeedOverGround() {
    return gps != null && gps.getFixStatus() != GpsFix.NONE ? gps.getSpeedOverGround() : 0.0;
  }

  @GetMapping( path = "/cog" )
  public int getCourseOverGround() {
    return gps != null && gps.getFixStatus() != GpsFix.NONE ? (int) Math.round( gps.getCourseOverGround() ) : 0;
  }

  @GetMapping( path = "/variation" )
  public double getVariation() {
    return gps != null && gps.getFixStatus() != GpsFix.NONE ? gps.getVariation() : 0.0;
  }

  @GetMapping( path = "/satellites" )
  public List<SatelliteInfo> getSatellitesInView() {
    if( gps == null ) {
      return null;
    }

    Map<Integer, SatelliteInfo> satellites = gps.getSatellitesInView();

    if( gps.getFixStatus() != GpsFix.NONE ) {
      for( int prn : gps.getPRNs() ) {
        satellites.get( prn ).setFix( true );
      }
    }

    return satellites.values().stream().collect( Collectors.toList() );
  }

  public void setGpsAntenna( GpsAntenna gps ) {
    this.gps = gps;
  }
}
