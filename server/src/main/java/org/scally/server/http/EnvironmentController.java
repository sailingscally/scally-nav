package org.scally.server.http;

import org.scally.server.services.AtmosphericPressure;
import org.scally.server.services.EnvironmentMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/environment" )
public class EnvironmentController {

  private Logger logger = LoggerFactory.getLogger( EnvironmentController.class );

  private EnvironmentMonitor monitor;

  @GetMapping( path = "/temperature", produces = MediaType.APPLICATION_JSON_VALUE )
  public double getTemperature() {
    return monitor.getTemperature();
  }

  @GetMapping( path = "/pressure", produces = MediaType.APPLICATION_JSON_VALUE )
  public double getPressure() {
    return monitor.getPressure();
  }

  @GetMapping( path = "/humidity", produces = MediaType.APPLICATION_JSON_VALUE )
  public double getHumidity() {
    return monitor.getHumidity();
  }

  @GetMapping( path = "/barograph", produces = MediaType.APPLICATION_JSON_VALUE )
  public List<AtmosphericPressure> getBarograph() {
    return monitor.getBarograph();
  }

  public void setEnvironmentMonitor( EnvironmentMonitor monitor ) {
    this.monitor = monitor;
  }
}
