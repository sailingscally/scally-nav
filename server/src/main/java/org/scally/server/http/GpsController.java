package org.scally.server.http;

import org.scally.server.core.gps.GpsAntenna;
import org.scally.server.core.gps.GpsFix;
import org.scally.server.core.gps.GpsPosition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping( "/api/gps" )
public class GpsController {

  private GpsAntenna gps;

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @GetMapping( path = "/status" )
  public GpsFix status() {
    return gps != null ? gps.getFixStatus() : null;
  }

  @GetMapping( path = "/position" )
  public GpsPosition position() {
    return gps != null ? gps.getPosition() : null;
  }

  public void setGpsAntenna( GpsAntenna gps ) {
    this.gps = gps;
  }
}
