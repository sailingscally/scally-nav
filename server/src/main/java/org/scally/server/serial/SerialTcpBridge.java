package org.scally.server.serial;

import org.scally.server.core.serial.SerialLineProcessor;
import org.scally.server.tcp.TcpServer;

public class SerialTcpBridge implements SerialLineProcessor {

  private TcpServer server;

  public void setTcpServer( TcpServer server ) {
    this.server = server;
  }

  @Override
  public void processLine( String line ) {
    if( server != null ) {
      server.notifyListeners( line );
    }
  }
}
