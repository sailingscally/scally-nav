package org.scally.server.http;

import org.scally.server.core.sensors.env.BME280;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/environment" )
public class EnvironmentController {

  private Logger logger = LoggerFactory.getLogger( EnvironmentController.class );

  private BME280 bme;

  @GetMapping( path = "/temperature" )
  public double getTemperature() {
    try {
      return bme != null ? bme.read().getTemperature() : 0.0;
    } catch( InterruptedException e ) {
      logger.debug( e.getMessage(), e );
      return 0.0;
    }
  }

  @GetMapping( path = "/pressure()" )
  public double getPressure() {
    try {
      return bme != null ? bme.read().getPressure() : 0.0;
    } catch( InterruptedException e ) {
      logger.debug( e.getMessage(), e );
      return 0.0;
    }
  }

  @GetMapping( path = "/humidity()" )
  public double getHumidity() {
    try {
      return bme != null ? bme.read().getHumidity() : 0.0;
    } catch( InterruptedException e ) {
      logger.debug( e.getMessage(), e );
      return 0.0;
    }
  }

  public void setEnvironmentSensor( BME280 bme ) {
    this.bme = bme;
  }
}
