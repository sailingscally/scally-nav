package org.scally.server;

import org.scally.server.core.gps.GpsAntenna;
import org.scally.server.tcp.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Skipper {

  private static ConfigurableApplicationContext context;
  private static GpsAntenna gps = new GpsAntenna();

  public static void main( String[] args ) {
    context = SpringApplication.run( Skipper.class, args );
    context.getBean( SerialPort.class ).addProcessor( gps );
  }

//   public Skipper() {
    // TcpServer server = context.getBean( TcpServer.class );
    // server.run();

    // MyThread myThread = context.getBean( MyThread.class, server );
    // myThread.run();

//   }
}
