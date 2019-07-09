package org.scally.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class Application {

  public static void main( String[] args ) {
    // https://www.baeldung.com/spring-boot-change-port
    ConfigurableApplicationContext context = SpringApplication.run( Application.class, args );

    TcpServer server = context.getBean( TcpServer.class );
    server.run();

    MyThread myThread = context.getBean( MyThread.class, server );
    //    service.setTcpServer( server );
    myThread.run();
  }
}
