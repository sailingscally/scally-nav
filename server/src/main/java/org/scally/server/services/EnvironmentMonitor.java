package org.scally.server.services;

import org.scally.server.core.sensors.env.BME280;
import org.scally.server.core.sensors.env.BME280Data;
import org.scally.server.http.EnvironmentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Service
public class EnvironmentMonitor {

  private static final int BUFFER_SIZE = 16; // take 16 readings and average them
  private static final int BAROGRAPH_LENGTH = 96; // keep a record of the last 48 hours in 30 minute intervals

  private Logger logger = LoggerFactory.getLogger( EnvironmentController.class );

  private double[] temperature = new double[ BUFFER_SIZE ];
  private double[] pressure = new double[ BUFFER_SIZE ];
  private double[] humidity = new double[ BUFFER_SIZE ];
  
  private LinkedList<AtmosphericPressure> barograph = new LinkedList<>();
  
  int index = 0;
  
  private BME280 bme;

  @Async( "executor" )
  public void run() {
    try {
      while ( true ) {
        if( bme == null ) {
          continue;
        }

        BME280Data data = bme.read();

        if( data != null ) {
          temperature[index] = data.getTemperature();
          pressure[index] = data.getPressure();
          humidity[index] = data.getHumidity();

          index = ++ index == BUFFER_SIZE ? 0 : index; // loop the buffer

          if( Calendar.getInstance().get( Calendar.MINUTE ) % 30 == 0 ) {
            Calendar time = Calendar.getInstance();
            time.clear( Calendar.MILLISECOND );
            time.clear( Calendar.SECOND );

            if( barograph.stream().filter( x -> x.getTime().equals( time ) ).count() == 0 ) {
              barograph.addLast( new AtmosphericPressure( time.toInstant(), getPressure() ) );
            }

            if( barograph.size() > BAROGRAPH_LENGTH ) {
              barograph.removeFirst();
            }
          }
        }
        
        Thread.sleep( 30000 ); // take a new reading every 30 seconds
      }
    } catch ( InterruptedException e ) {
      // ignore... this happens when the server is shutting down
    }
  }

  public double getTemperature() {
    return getAverage( temperature );
  }

  public double getPressure() {
    return getAverage( pressure );
  }

  public double getHumidity() {
    return getAverage( humidity );
  }

  /**
   * Returns the atmospheric pressure history. If the system has been running
   * for less than 48 hours there will be less than 96 values stored.
   */
  public List<AtmosphericPressure> getBarograph() {
    return barograph;
  }

  private double getAverage( double[] data ) {
    double average = 0.0;
    int count = 0;

    for( double value : data ) {
      if( value == 0.0 ) {
        break;
      }

      average += value;
      count ++;
    }

    return count != 0 ? average / ( count * 1.0 ) : 0.0;
  }

  public void setEnvironmentSensor( BME280 bme ) {
    this.bme = bme;
  }
}
