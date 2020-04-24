package org.scally.server.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TcpServer {

  private static final int SERVER_PORT = 8085;
  private static final String NEW_LINE = "\r\n";

  @Autowired
  private TcpServerHandler handler;

  private Map<SocketAddress, Socket> listeners = new ConcurrentHashMap<>();
  private Set<Socket> l = ConcurrentHashMap.newKeySet();

  @Async
  public void run() {
    try ( ServerSocket server = new ServerSocket( SERVER_PORT ) ) {
      while ( true ) {
        System.out.println("Waiting for clients on port " + server.getLocalPort() + "...");
        handler.handle( server.accept(), this );
      }
    } catch ( IOException e ) {
      e.printStackTrace();
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

      try {
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
        writer.write( message + NEW_LINE ); // must use '\r\n' since the client system is unknown
        writer.flush();
      } catch ( IOException e ) {
        e.printStackTrace();
      }
    }
  }
}
