package org.scally.server.tcp;

import java.net.Socket;

public interface TcpServerHandler {

  void handle( Socket socket, TcpServer server );
}
