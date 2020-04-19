package org.scally.server;

import org.scally.server.tcp.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Skipper {

  public static void main( String[] args ) {
    // https://www.baeldung.com/spring-boot-change-port
    ConfigurableApplicationContext context = SpringApplication.run( Skipper.class, args );

    TcpServer server = context.getBean( TcpServer.class );
    server.run();

    MyThread myThread = context.getBean( MyThread.class, server );
    myThread.run();
  }
}
