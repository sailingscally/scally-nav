package org.scally.spikes;

import org.scally.server.core.net.Network;
import org.scally.server.core.oled.Align;
import org.scally.server.core.oled.Font;
import org.scally.server.core.oled.FontFactory;
import org.scally.server.core.oled.FontNotFoundException;
import org.scally.server.core.oled.Glyph;
import org.scally.server.core.oled.GlyphNotFoundException;
import org.scally.server.core.oled.SSD1306;
import org.scally.server.core.oled.fonts.Grand9K;
import org.scally.server.core.sensors.adc.ADS1015;
import org.scally.server.core.sensors.adc.Channel;
import org.scally.server.core.sensors.adc.Gain;
import org.scally.server.core.sensors.env.BME280;
import org.scally.server.core.sensors.env.BME280Data;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySSD1306 {

  public static void main( String[] args ) throws Exception {
    SSD1306 display = new SSD1306();

    BME280 bme280 = new BME280();

    ADS1015 ads = new ADS1015();
    ads.setGain( Gain.ONE );

    /*
    for( int i = 0; i < 5; i ++ ) {
      Thread.sleep( 1000 );
      display.invert();
      Thread.sleep( 1000 );
      display.normal();
    }

    for(int y = 0; y < display.getHeight(); y ++) {
      for(int x = 0; x < display.getWidth(); x ++) {
        display.point( x, y );
        display.display();

        System.out.format( "x: %d, y: %d\n", x, y );
        Thread.sleep( 10 );
      }
    }

    for(int y = 0; y < display.getHeight(); y ++) {
      for(int x = 0; x < display.getWidth(); x ++) {
        display.point( x, y, true );
        display.display();

        System.out.format( "x: %d, y: %d\n", x, y );
        Thread.sleep( 10 );
      }
    }

    display.clear();
*/

    // start by printing a splash logo
    printLogoImage( display );

    Thread.sleep( 2000 );

    while( true ) {
      display.clear();

      // page #0
      printNameText( display );
      printSystemDate(display);

      // page #1
      printEnvironmentData( display, bme280 );

      // page #2
      printSystemVoltage( display, ads );

      // page #3
      printNetworkAddress( display );
      printSystemTime( display );

      display.display();

      Thread.sleep( 5000 );
    }

    // display.shutdown();
  }

  public static void printLogoImage( SSD1306 display ) throws IOException {
    int[] image = new int[] {
      0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x80,
      0xC0, 0xE0, 0x78, 0x3E, 0x80, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
      0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7C, 0xFE, 0xC7, 0x03,
      0x7D, 0xFE, 0xE7, 0xC3, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
      0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0C, 0x0C, 0x6E, 0x6A, 0x6A, 0xC8, 0xD8, 0xC9, 0xC9,
      0xC8, 0xCF, 0x6B, 0x6D, 0x4C, 0x04, 0x00, 0x42, 0x62, 0x3E, 0x1E, 0x00, 0x00, 0x00, 0x00, 0x00,
      0x00, 0x00, 0x00, 0x00, 0x00, 0x18, 0x1C, 0x14, 0x34, 0x70, 0x73, 0x73, 0x76, 0x76, 0x76, 0x76,
      0x76, 0x76, 0x77, 0x72, 0x72, 0x50, 0x70, 0x78, 0x78, 0x3C, 0x30, 0x00, 0x00, 0x00, 0x00, 0x00
    };

    byte[] buffer = new byte[ image.length ];

    for( int i = 0; i < image.length; i ++ ) {
      buffer[i] = (byte) image[i];
    }

    display.display( buffer, 48, 0, 32, 32 / 8 );
  }

  public static void printNameText( SSD1306 display ) throws FontNotFoundException, GlyphNotFoundException, IOException {
    print( "Scally", 0, display, FontFactory.getFont( Grand9K.NAME ) );
  }

  public static void printSystemDate( SSD1306 display ) throws FontNotFoundException, GlyphNotFoundException, IOException {
    print( new SimpleDateFormat( "dd-MMM-yyyy" ).format( new Date() ), 0, display, FontFactory.getFont( Grand9K.NAME ), Align.RIGHT );
  }

  public static void printEnvironmentData( SSD1306 display, BME280 bme280 ) throws InterruptedException,
    FontNotFoundException, GlyphNotFoundException, IOException {

    BME280Data data = bme280.read();

    print( String.format( "%.0f ºC", data.getTemperature() ), 1, display, FontFactory.getFont( Grand9K.NAME ) );
    print( String.format( "%.0f hPa", data.getPressure() ), 1, display, FontFactory.getFont( Grand9K.NAME ), Align.CENTER );
    print( String.format( "%.0f %%", data.getHumidity() ), 1, display, FontFactory.getFont( Grand9K.NAME ), Align.RIGHT );
  }

  public static void printSystemVoltage( SSD1306 display, ADS1015 ads ) throws InterruptedException,
    FontNotFoundException, GlyphNotFoundException, IOException {

    print( String.format( "V1: %.1f", ads.readSingleEnded( Channel.ZERO ) ), 2, display, FontFactory.getFont( Grand9K.NAME ) );
    print( String.format( "V2: %.1f", ads.readSingleEnded( Channel.ONE ) ), 2, display, FontFactory.getFont( Grand9K.NAME ), Align.CENTER );
    print( String.format( "A: %.1f", ads.readSingleEnded( Channel.TWO_THREE ) ), 2, display, FontFactory.getFont( Grand9K.NAME ), Align.RIGHT );
  }

  public static void printSystemTime( SSD1306 display ) throws FontNotFoundException, GlyphNotFoundException, IOException {
    print( new SimpleDateFormat( "HH:mm:ss" ).format( new Date() ), 3, display, FontFactory.getFont( Grand9K.NAME ) );
  }

  public static void printNetworkAddress( SSD1306 display ) throws FontNotFoundException, GlyphNotFoundException, IOException {
    print( Network.getLocalIPAddress(), 3, display, FontFactory.getFont( Grand9K.NAME ), Align.RIGHT );
  }

  public static void print( String text, int page, SSD1306 display, Font font ) throws GlyphNotFoundException, IOException {
    print( text, page, display, font, Align.LEFT );
  }

  /**
   * Page is zero based (on a 128x32 pixels screen there are four pages from 0 to 3)
   */
  public static void print( String text, int page, SSD1306 display, Font font, Align align ) throws GlyphNotFoundException, IOException {
    byte[] buffer = display.getBuffer();
    int column = display.getWidth() * page;

    if( align != Align.LEFT ) {
      int width = font.getTextWidth( text ); // width in pixels
      column += align == Align.RIGHT ? display.getWidth() - width : ( display.getWidth() - width ) / 2; // shift offset
    }

    for( int i = 0; i < text.length(); i ++ ) {
      Glyph glyph = font.getGlyph( text.charAt( i ) );

      for( byte b : glyph.getData() ) {
        buffer[column ++] = b;
      }

      column += font.getSpaceWidth();
    }

    display.setBuffer( buffer );
  }
}
