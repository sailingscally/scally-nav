package org.scally.server.tcp;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;

@Component
public class BroadcastHandler implements TcpServerHandler {

  @Override
  @Async( "executor" )
  public void handle( Socket socket, TcpServer server ) {
    server.addListener( socket );

    try ( BufferedReader reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) ) ) {
      while ( socket.isConnected() ) {
        server.notifyListeners( reader.readLine(), socket.getRemoteSocketAddress() );
      }

      server.removeListener( socket );
    } catch ( IOException e ) {
      System.out.println( "Got an exception in handler " + Thread.currentThread().getName() );
    }
  }
}
