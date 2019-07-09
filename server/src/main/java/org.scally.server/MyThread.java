package org.scally.server;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MyThread {

  private TcpServer server;

  public MyThread( TcpServer server ) {
    this.server = server;
  }

  @Async
  public void run() {
    try {
      while ( true ) {
        Thread.sleep( 1000 );
        System.out.println( "threads....." );

        // server.notifyListeners( "$GPGGA,123519,4807.038,N,01131.000,E,1,08,0.9,545.4,M,46.9,M,,*47" );

        // https://fossies.org/linux/gpsd/test/sample.aivdm
        server.notifyListeners( "!AIVDM,1,1,,A,15RTgt0PAso;90TKcjM8h6g208CQ,0*4A" );
        server.notifyListeners( "!AIVDM,1,1,,A,16SteH0P00Jt63hHaa6SagvJ087r,0*42" );
        server.notifyListeners( "!AIVDM,1,1,,B,25Cjtd0Oj;Jp7ilG7=UkKBoB0<06,0*60" );

        // https://en.wikipedia.org/wiki/NMEA_0183
        server.notifyListeners( "$GPGGA,092750.000,5321.6802,N,00630.3372,W,1,8,1.03,61.7,M,55.2,M,,*76" );
        server.notifyListeners( "$GPGSA,A,3,10,07,05,02,29,04,08,13,,,,,1.72,1.03,1.38*0A" );
        server.notifyListeners( "$GPGSV,3,1,11,10,63,137,17,07,61,098,15,05,59,290,20,08,54,157,30*70" );
        server.notifyListeners( "$GPGSV,3,2,11,02,39,223,19,13,28,070,17,26,23,252,,04,14,186,14*79" );
        server.notifyListeners( "$GPGSV,3,3,11,29,09,301,24,16,09,020,,36,,,*76" );
        server.notifyListeners( "$GPRMC,092750.000,A,5321.6802,N,00630.3372,W,0.02,31.66,280511,,,A*43" );
        server.notifyListeners( "$GPGGA,092751.000,5321.6802,N,00630.3371,W,1,8,1.03,61.7,M,55.3,M,,*75" );
        server.notifyListeners( "$GPGSA,A,3,10,07,05,02,29,04,08,13,,,,,1.72,1.03,1.38*0A" );
        server.notifyListeners( "$GPGSV,3,1,11,10,63,137,17,07,61,098,15,05,59,290,20,08,54,157,30*70" );
        server.notifyListeners( "$GPGSV,3,2,11,02,39,223,16,13,28,070,17,26,23,252,,04,14,186,15*77" );
        server.notifyListeners( "$GPGSV,3,3,11,29,09,301,24,16,09,020,,36,,,*76" );
        server.notifyListeners( "$GPRMC,092751.000,A,5321.6802,N,00630.3371,W,0.06,31.66,280511,,,A*45" );
      }
    } catch ( InterruptedException e ) {
      // ignore... this happens when the server is shutting down
    }
  }
}
