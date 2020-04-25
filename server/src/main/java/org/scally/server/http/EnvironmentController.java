package org.scally.server.http;

import org.scally.server.services.AtmosphericPressure;
import org.scally.server.services.EnvironmentMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/environment" )
public class EnvironmentController {

  private Logger logger = LoggerFactory.getLogger( EnvironmentController.class );

  private EnvironmentMonitor monitor;

  @GetMapping( path = "/temperature" )
  public double getTemperature() {
    return monitor.getTemperature();
  }

  @GetMapping( path = "/pressure" )
  public double getPressure() {
    return monitor.getPressure();
  }

  @GetMapping( path = "/humidity" )
  public double getHumidity() {
    return monitor.getHumidity();
  }

  @GetMapping( path = "/barograph" )
  public List<AtmosphericPressure> getBarograph() {
    return monitor.getBarograph();
  }

  public void setEnvironmentMonitor( EnvironmentMonitor monitor ) {
    this.monitor = monitor;
  }
}
