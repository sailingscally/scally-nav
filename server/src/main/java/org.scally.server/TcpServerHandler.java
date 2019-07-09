package org.scally.server;

import java.net.Socket;

public interface TcpServerHandler {

  void handle( Socket socket, TcpServer server );
}
