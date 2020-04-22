package org.scally.spikes;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calc {

  public static void main( String[] args ) throws Exception {

    String[] ip = new String[4];
    int i = 0;


    for( int x : Inet4Address.getLocalHost().getAddress() ) {
      ip[ i ++ ] = String.format( "%d", x & 0xFF );
    }

    String.join( ".", ip );

    new SimpleDateFormat( "HH:mm:ss dd-MM-yyyy" ).format( new Date() );

  }
}
