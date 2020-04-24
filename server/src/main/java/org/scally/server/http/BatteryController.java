package org.scally.server.http;

import org.scally.server.core.sensors.adc.ADS1015;
import org.scally.server.core.sensors.adc.Channel;
import org.scally.server.core.sensors.adc.Gain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/battery" )
public class BatteryController {

  private Logger logger = LoggerFactory.getLogger( BatteryController.class );

  private ADS1015 ads;

  @GetMapping( path = "/primary" )
  public double getPrimary() {
    try {
      return ads != null ? ads.readSingleEnded( Channel.ZERO, Gain.ONE ) : 0.0;
    } catch ( InterruptedException e ) {
      logger.debug( e.getMessage(), e );
      return 0.0;
    }
  }

  @GetMapping( path = "/starter" )
  public double getStarter() {
    try {
      return ads != null ? ads.readSingleEnded( Channel.ONE, Gain.ONE ) : 0.0;
    } catch ( InterruptedException e ) {
      logger.debug( e.getMessage(), e );
      return 0.0;
    }
  }

  @GetMapping( path = "/current" )
  public double getCurrent() {
    try {
      // TODO: adjust gain based on shunt resistance and amplifier gain
      return ads != null ? ads.readDifferential( Channel.TWO_THREE, Gain.ONE ) : 0.0;
    } catch ( InterruptedException e ) {
      logger.debug( e.getMessage(), e );
      return 0.0;
    }
  }

  public void setAnalogDigitalConverter( ADS1015 ads ) {
    this.ads = ads;
  }
}
