package org.scally.server;

import org.scally.server.core.gps.GpsAntenna;
import org.scally.server.serial.SerialPort;
import org.scally.server.serial.SerialTcpBridge;
import org.scally.server.tcp.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Skipper {

  public static void main( String[] args ) {
    ConfigurableApplicationContext context = SpringApplication.run( Skipper.class, args );

    GpsAntenna gps = new GpsAntenna();

    SerialTcpBridge bridge = new SerialTcpBridge();
    bridge.setTcpServer( context.getBean( TcpServer.class ) );

    // MyThread myThread = context.getBean( MyThread.class, server );
    // myThread.run();

    context.getBean( SerialPort.class ).addProcessor( gps );
    context.getBean( SerialPort.class ).addProcessor( bridge );

    context.getBean( TcpServer.class ).run();
  }
}
