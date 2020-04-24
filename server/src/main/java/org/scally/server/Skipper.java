package org.scally.server;

import com.pi4j.io.i2c.I2CFactory;
import org.scally.server.core.gps.GpsAntenna;
import org.scally.server.core.sensors.DeviceNotFoundException;
import org.scally.server.core.sensors.adc.ADS1015;
import org.scally.server.core.sensors.adc.Gain;
import org.scally.server.core.sensors.env.BME280;
import org.scally.server.http.BatteryController;
import org.scally.server.http.EnvironmentController;
import org.scally.server.http.GpsController;
import org.scally.server.serial.SerialPort;
import org.scally.server.serial.SerialTcpBridge;
import org.scally.server.tcp.TcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
public class Skipper {

  private Logger logger = LoggerFactory.getLogger( Skipper.class );

  private GpsAntenna gps = new GpsAntenna();
  private SerialTcpBridge bridge = new SerialTcpBridge();

  private ADS1015 ads;
  private BME280 bme;

  private Lock i2cLock = new ReentrantLock();
  private Lock spicLock = new ReentrantLock();

  public static void main( String[] args ) {
    SpringApplication.run( Skipper.class, args );
  }

  public Skipper( ConfigurableApplicationContext context ) {
    TcpServer tcp = context.getBean( TcpServer.class );
    tcp.run();
    bridge.setTcpServer( tcp );

    try {
      ads = new ADS1015( i2cLock );
      ads.setGain( Gain.ONE );
    } catch ( I2CFactory.UnsupportedBusNumberException e ) {
      logger.debug( e.getMessage(), e );
    } catch ( DeviceNotFoundException e ) {
      logger.error( e.getMessage(), e );
    }

    try {
      bme = new BME280( i2cLock );

    } catch ( I2CFactory.UnsupportedBusNumberException e ) {
      logger.debug( e.getMessage(), e );
    } catch ( DeviceNotFoundException e ) {
      logger.error( e.getMessage(), e );
    } catch ( InterruptedException e ) {
      logger.error( e.getMessage(), e );
    }

    // MyThread myThread = context.getBean( MyThread.class, server );
    // myThread.run();

    context.getBean( GpsController.class ).setGpsAntenna( gps );
    context.getBean( BatteryController.class ).setAnalogDigitalConverter( ads );
    context.getBean( EnvironmentController.class ).setEnvironmentSensor( bme );

    context.getBean( SerialPort.class ).addProcessor( gps );
    context.getBean( SerialPort.class ).addProcessor( bridge );
  }
}
