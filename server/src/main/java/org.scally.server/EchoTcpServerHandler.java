package org.scally.server;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;

@Component
public class EchoTcpServerHandler implements TcpServerHandler {

  @Override
  @Async( "tcpServerHandler" ) // https://github.com/cawak/tcp-server
  public void handle( Socket socket, TcpServer server ) {
    System.out.println( LocalDateTime.now() + ": " + Thread.currentThread().getName() + " handling client " + socket.getRemoteSocketAddress() );

    server.addListener( socket );

    try ( BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
          BufferedReader reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) ) ) {
      while ( socket.isConnected() ) {
        writer.write( ">>> echo: " + reader.readLine() + System.lineSeparator() );
        writer.flush();
      }

      server.removeListener( socket );
    } catch ( IOException e ) {
      System.out.println( "Got an exception in handler " + Thread.currentThread().getName() );
    }
  }
}
