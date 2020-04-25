package org.scally.server.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TcpServer {

  private Logger logger = LoggerFactory.getLogger( TcpServer.class );

  private static final int SERVER_PORT = 8085;
  private static final String NEW_LINE = "\r\n";

  @Autowired
  private TcpServerHandler handler;

  private Map<SocketAddress, Socket> listeners = new ConcurrentHashMap<>();
  private Set<Socket> l = ConcurrentHashMap.newKeySet();

  @Async( "executor" )
  public void run() {
    logger.info( "Starting TCP server on port {}.", SERVER_PORT );

    try ( ServerSocket server = new ServerSocket( SERVER_PORT ) ) {
      while ( true ) {
        handler.handle( server.accept(), this );
      }
    } catch ( IOException e ) {
      logger.error( "Error starting TCP server on port {}.", e );
    }
  }

  public void addListener( Socket socket ) {
    listeners.put( socket.getRemoteSocketAddress(), socket );
  }

  public void removeListener( Socket socket ) {
    listeners.remove( socket.getRemoteSocketAddress() );
  }

  public void notifyListeners( String message ) {
    notifyListeners( message, null );
  }

  public synchronized void notifyListeners( String message, SocketAddress listener ) {
    for( SocketAddress key : listeners.keySet() ) {
      if( key.equals( listener ) ) {
        continue;
      }

      Socket socket = listeners.get( key );

      if( socket.isClosed() ) {
        removeListener( socket );
        return;
      }

      try {
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
        writer.write( message + NEW_LINE ); // must use '\r\n' since the client system is unknown
        writer.flush();
      } catch ( SocketException e ) {
        removeListener( socket );

        try {
          socket.close();
        } catch ( IOException io ) {
          logger.debug( "Error closing listener socket after a broadcast error.", io );
        }
        logger.error( "Error broadcasting data to TCP listeners, most likely the client disconnected.", e );
      } catch ( IOException e ) {
        logger.error( "Error broadcasting data to TCP listeners.", e );
      }
    }
  }
}
