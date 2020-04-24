package org.scally.server;

import org.scally.server.core.serial.SerialLineProcessor;
import org.scally.server.tcp.TcpServer;

public class SerialTcpBridge implements SerialLineProcessor {

  private TcpServer server;

  public SerialTcpBridge setTcpServer( TcpServer server ) {
    this.server = server;
    return this;
  }

  @Override
  public void processLine( String line ) {
    if( server != null ) {
      server.notifyListeners( line );
    }
  }
}
