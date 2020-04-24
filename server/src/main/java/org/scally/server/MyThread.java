package org.scally.server;

import org.scally.server.tcp.TcpServer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MyThread {

  private TcpServer server;

  public MyThread( TcpServer server ) {
    this.server = server;
  }

  @Async
  public void run() {
    try {
      while ( true ) {
        Thread.sleep( 1000 );
        System.out.println( "threads....." );

        server.notifyListeners( new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) );
      }
    } catch ( InterruptedException e ) {
      // ignore... this happens when the server is shutting down
    }
  }
}
