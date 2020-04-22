package org.scally.server.core.oled;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;
import org.scally.server.core.DeviceInitializationException;
import org.scally.server.core.DeviceNotFoundException;

import java.io.IOException;

/**
 * Datasheet can be found at: https://cdn-shop.adafruit.com/datasheets/SSD1306.pdf
 *
 * Portions of this have been ported from:
 *   - https://github.com/ondryaso/pi-ssd1306-java/blob/master/src/eu/ondryaso/ssd1306/Display.java
 *   - https://github.com/adafruit/Adafruit_SSD1306/blob/master/Adafruit_SSD1306.cpp
 *
 */
public class SSD1306 {

  /**
   * The SSD1306 has a default dimension of 128x32.
   */
  public static final int WIDTH = 128;
  public static final int HEIGHT = 32;

  /**
   * Constants for device configuration.
   */
  public static final int CLOCK_FREQUENCY = 8000000;

  public static final int SET_DISPLAY_OFF = 0xAE;
  public static final int SET_DISPLAY_ON = 0xAF;
  public static final int SET_DISPLAY_ON_RESUME = 0xA4;

  public static final int SET_NORMAL_DISPLAY = 0xA6;
  public static final int SET_INVERSE_DISPLAY = 0xA7;

  public static final int SET_DISPLAY_CLOCK_DIVIDE = 0xD5;
  public static final int RATIO_FREQUENCY = 0x80; // ratio is the same for both 128x64 and 128x32 displays

  public static final int SET_MULTIPLEX_RATIO = 0xA8;
  public static final int MULTIPLEX_RATIO_128x32 = 0x1F;
  public static final int MULTIPLEX_RATIO_128x64 = 0x3F;

  public static final int SET_DISPLAY_OFFSET = 0xD3;
  public static final int DEFAULT_DISPLAY_OFFSET = 0x00;

  public static final int SET_START_LINE = 0x40; // start at line 0

  public static final int SET_CHARGE_PUMP = 0x8D;
  public static final int CHARGE_PUMP_EXTERNAL_VCC = 0x10;
  public static final int CHARGE_PUMP_SWITCHING_CAPACITOR = 0x14; // default

  public static final int SET_MEMORY_ADDRESSING_MODE = 0x20;
  public static final int HORIZONTAL_ADDRESSING_MODE = 0x00; // we're only using horizontal addressing mode

  public static final int SET_SEGMENT_REMAP = 0xA1;
  public static final int SET_COM_OUTPUT_SCAN_DIRECTION = 0xC8;

  public static final int SET_COM_PINS = 0xDA;
  public static final int COM_PINS_128x32 = 0x02;
  public static final int COM_PINS_128x64 = 0x12;

  public static final int SET_CONTRAST = 0x81;
  public static final int DEFAULT_CONTRAST = 0x8F; // this value can be adjusted if we're using an external vcc

  public static final int SET_PRE_CHARGE_PERIOD = 0xD9;
  public static final int DEFAULT_PRE_CHARGE_PERIOD = 0xF1; // change to 0x22 if using an external vcc

  public static final int SET_VCOMH_DESELECT_LEVEL = 0xDB;
  public static final int DEFAULT_VCOMH_DESELECT_LEVEL = 0x40;

  public static final int SET_COLUMN_ADDRESS = 0x21;
  public static final int SET_PAGE_ADDRESS = 0x22;

  /**
   * Default D/C (data / command) and RESET pins.
   */
  public static final Pin DC_PIN = RaspiPin.GPIO_04; // GPIO 23
  public static final Pin RESET_PIN = RaspiPin.GPIO_05; // GPIO 24

  private int width;
  private int height;

  private int pages;
  private byte[] buffer;

  GpioController gpio;
  private GpioPinDigitalOutput dc;
  private GpioPinDigitalOutput rst;

  private SpiDevice spi;

  public SSD1306() throws DeviceInitializationException, DeviceNotFoundException {
    this( WIDTH, HEIGHT );
  }

  public SSD1306( int width, int height ) throws DeviceInitializationException, DeviceNotFoundException {
    this( width, height, DC_PIN, RESET_PIN );
  }

  public SSD1306( int width, int height, Pin dc, Pin rst ) throws DeviceInitializationException, DeviceNotFoundException {
    this.width = width;
    this.height = height;

    pages = height / 8;
    buffer = new byte[ width * pages ];

    gpio = GpioFactory.getInstance();
    this.dc = gpio.provisionDigitalOutputPin( dc, "DC", PinState.LOW ); // start in command mode
    this.rst = gpio.provisionDigitalOutputPin( rst, "RST", PinState.HIGH ); // reset is active on LOW

    try {
      spi = SpiFactory.getInstance( SpiChannel.CS0, CLOCK_FREQUENCY );
    } catch ( IOException e ) {
      throw new DeviceNotFoundException( "Unable to acquire SPI channel.", e );
    }

    try {
      reset();
      init();
    } catch ( InterruptedException | IOException e ) {
      throw new DeviceInitializationException( "Unable to initialize SSD1306 display.", e );
    }
  }

  private void init() throws IOException {
    command( SET_DISPLAY_OFF );

    command( SET_DISPLAY_CLOCK_DIVIDE );
    command( RATIO_FREQUENCY );

    command( SET_MULTIPLEX_RATIO );
    command( height == HEIGHT ? MULTIPLEX_RATIO_128x32 : MULTIPLEX_RATIO_128x64 ); // default height is 32

    command( SET_DISPLAY_OFFSET );
    command( DEFAULT_DISPLAY_OFFSET );

    command( SET_START_LINE );

    command( SET_CHARGE_PUMP );
    command( CHARGE_PUMP_SWITCHING_CAPACITOR ); // change this if an external vcc is used for the OLED display

    command( SET_MEMORY_ADDRESSING_MODE );
    command( HORIZONTAL_ADDRESSING_MODE );

    command( SET_SEGMENT_REMAP );
    command( SET_COM_OUTPUT_SCAN_DIRECTION );

    command( SET_COM_PINS );
    command( height == HEIGHT ? COM_PINS_128x32 : COM_PINS_128x64 ); // default height is 32

    command( SET_CONTRAST );
    command( DEFAULT_CONTRAST );

    command( SET_PRE_CHARGE_PERIOD );
    command( DEFAULT_PRE_CHARGE_PERIOD );

    command( SET_VCOMH_DESELECT_LEVEL );
    command( DEFAULT_VCOMH_DESELECT_LEVEL );

    command( SET_DISPLAY_ON_RESUME );
    command( SET_NORMAL_DISPLAY );

    command( SET_DISPLAY_ON );
  }

  /**
   * Sets the contrast of the display to a level between 0 and 255.
   */
  public void setContrast( int level ) throws IOException {
    command( SET_CONTRAST );
    command( level );
  }

  /**
   * Resets the SSD1306 display.
   * Set the reset pin to LOW for 10 milliseconds and bring it to HIGH again.
   */
  private void reset() throws InterruptedException {
    rst.low();
    Thread.sleep( 10 );
    rst.high();
  }

  public void invert() throws IOException {
    command( SET_INVERSE_DISPLAY );
  }

  public void normal() throws IOException {
    command( SET_NORMAL_DISPLAY );
  }

  public void shutdown() {
    gpio.shutdown();
  }

  /**
   * Sends a command to the OLED display, to send a command the D/C pin must be LOW.
   */
  private void command( int command ) throws IOException {
    dc.low();
    spi.write( (short) command );
  }

  /**
   * Sends data to the OLED display, to send data the D/C pin must be HIGH.
   */
  private void write( byte[] data ) throws IOException {
    dc.high();
    spi.write( data );
  }

  /**
   * Resets the in-memory display buffer that contains data to be sent to the OLED display.
   */
  public void clear() {
    buffer = new byte[ width * pages ];
  }

  /**
   * Sends the in-memory display buffer to the OLED display.
   *
   * This is the most inefficient method of sending data to the OLED display, it assumes that the whole
   * display will be written with data from the in-memory buffer and doesn't allow for small portions
   * of the display to be updated.
   */
  public void display() throws IOException {
    display( buffer, 0, 0, width, pages );
  }

  /**
   * Sends the given array of bytes to the OLED screen, allowing for an efficient method of updating small portions
   * of the screen without re-writing the entire screen.
   *
   * @param buffer The array of data to render to the OLED display
   * @param x The horizontal offset from where to start drawing
   * @param y The vertical offset in pages (8 pixels high) from where to start drawing
   * @param width The with of the data to render in pixels
   * @param pages The height of the data to render in pages (8 pixels high)
   */
  public synchronized void display( byte[] buffer, int x, int y, int width, int pages ) throws IOException {
    this.command( SET_COLUMN_ADDRESS );
    this.command( x );
    this.command( x + width - 1 );
    this.command( SET_PAGE_ADDRESS );
    this.command( y );
    this.command( y + pages - 1 );

    write( buffer );
  }

  public void point( int x, int y ) {
    point( x, y, false );
  }

  public void point( int x, int y, boolean clear ) {
    int page = y / 8;
    byte value = (byte) ( 0x01 << y % 8 );

    int i = x + page * width;

    if( !clear ) {
      buffer[i] |= value;
    } else {
      buffer[i] ^= value;
    }
  }

  public byte[] getBuffer() {
    return buffer;
  }

  public void setBuffer( byte[] buffer ) {
    this.buffer = buffer;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
