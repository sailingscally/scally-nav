package org.scally.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TcpServer {

  @Autowired
  private TcpServerHandler handler;

  private Map<SocketAddress, Socket> listeners = new ConcurrentHashMap<>();
  private Set<Socket> l = ConcurrentHashMap.newKeySet();

  @Async
  public void run() {
    try ( ServerSocket server = new ServerSocket( 8085 ) ) {
      while ( true ) {
        System.out.println("Waiting for client on port " + server.getLocalPort() + "...");
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
    for( SocketAddress key : listeners.keySet() ) {
      Socket socket = listeners.get( key );

      try {
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
        writer.write( message + "\r\n" /* System.lineSeparator() */ );
        writer.flush();
      } catch ( IOException e ) {
        e.printStackTrace();
      }
    }
  }
}
