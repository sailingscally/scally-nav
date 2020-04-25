package org.scally.server.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Component
public class BroadcastHandler implements TcpServerHandler {

  private Logger logger = LoggerFactory.getLogger( BroadcastHandler.class );

  @Override
  @Async( "executor" )
  public void handle( Socket socket, TcpServer server ) {
    server.addListener( socket );

    try ( BufferedReader reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) ) ) {
      while ( socket.isConnected() ) {
        String line = reader.readLine();

        if( line == null ) {
          socket.close();
          break;
        }

        server.notifyListeners( line, socket.getRemoteSocketAddress() );
      }

      server.removeListener( socket );
    } catch ( IOException e ) {
      logger.debug( "Got an exception in handler '{}', most likely the client disconnected.", Thread.currentThread().getName() );
    }
  }
}
