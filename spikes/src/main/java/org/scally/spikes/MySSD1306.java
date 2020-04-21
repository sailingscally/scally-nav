package org.scally.spikes;

import org.scally.server.core.oled.SSD1306;

public class MySSD1306 {

  // create display object
// Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT,
  //  OLED_MOSI, OLED_CLK, OLED_DC, OLED_RESET, OLED_CS);


  //display.display() to show logo
  //sleep(2s)
  //  display.clear()

  // display.drawPixel(10, 10, SSD1306_WHITE); draw a single pixel
  // sleeps(2s)

  // draw a line
  // clear()

//  for(i=0; i<display.width(); i+=4) {
//    display.drawLine(0, 0, i, display.height()-1, SSD1306_WHITE);
//    display.display(); // Update screen with each newly-drawn line
//    delay(1);
//  }

  public static void main( String[] args ) throws Exception {
    SSD1306 display = new SSD1306();
    display.display();

    byte[] buffer = display.getBuffer();
    buffer[20] = (byte) 0xFF;
    buffer[21] = (byte) 0xFF;
    buffer[22] = (byte) 0xFF;
    buffer[23] = (byte) 0xFF;
    buffer[24] = (byte) 0xFF;
    buffer[25] = (byte) 0xFF;
    buffer[26] = (byte) 0xFF;
    buffer[27] = (byte) 0xFF;

    display.setBuffer( buffer );
    display.display();
  }
}
