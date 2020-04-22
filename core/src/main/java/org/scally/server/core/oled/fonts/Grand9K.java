package org.scally.server.core.oled.fonts;

import org.scally.server.core.oled.Font;
import org.scally.server.core.oled.Glyph;

public class Grand9K extends Font {

  public static final String NAME = "Grand9K Pixel Font";

  @Override
  public String getName() {
    return NAME;
  }

  public Grand9K() throws Exception {
    addGlyph( new Glyph( 'A', 1, 7, new byte[] {
      (byte) 0b00000000
    } ) );

    addGlyph( new Glyph( 'S', 5, 7, new byte[] {
      (byte) 0b01000110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110001
    } ) );

    addGlyph( new Glyph( 'a', 4, 7, new byte[] {
      (byte) 0b00000100,
      (byte) 0b00101010,
      (byte) 0b00101010,
      (byte) 0b00011110
    } ) );

    addGlyph( new Glyph( 'c', 3, 7, new byte[] {
      (byte) 0b00111000,
      (byte) 0b01000100,
      (byte) 0b01000100
    } ) );

    addGlyph( new Glyph( 'l', 1, 7, new byte[] {
      (byte) 0b01111110
    } ) );

    addGlyph( new Glyph( 'y', 4, 7, new byte[] {
      (byte) 0b10011100,
      (byte) 0b10100000,
      (byte) 0b10100000,
      (byte) 0b01111100
    } ) );
  }
}
