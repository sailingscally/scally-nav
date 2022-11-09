package org.scally.server.core.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Network {

  private static String ip;

  /**
   * Returns the local IP address of the Raspberry PI device.
   */
  public static String getLocalIPAddress() throws SocketException {
    if( ip == null ) {
      refresh();
    }
    return ip;
  }

  /**
   * Loop through the list of network interfaces and find the first interface that is not the loopback adapter.
   * For the first non-loopback, only the first IPv4 address is returned.
   */
  private static void refresh() throws SocketException {
    Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

    while( interfaces.hasMoreElements() ) {
      NetworkInterface network = (NetworkInterface) interfaces.nextElement();
      if( network.getInterfaceAddresses().stream().filter( x -> x.getAddress().isLoopbackAddress() ).count() != 0 ) {
        continue;
      }

      ip = network.getInterfaceAddresses().stream().filter( x -> isIPv4Address( x.getAddress() ) ).findFirst().get().getAddress().getHostAddress();
    }
  }

  /**
   * IPv4 addresses have 4 bytes (32 bits) while IPv6 addresses have 16 bytes (128 bits).
   */
  private static boolean isIPv4Address( InetAddress address ) {
    return address.getAddress().length == 4;
  }
}
