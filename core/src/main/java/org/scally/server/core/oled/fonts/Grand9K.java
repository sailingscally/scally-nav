package org.scally.server.core.oled.fonts;

import org.scally.server.core.oled.Font;
import org.scally.server.core.oled.Glyph;
import org.scally.server.core.oled.InvalidGlyphSizeException;

public class Grand9K extends Font {

  public static final String NAME = "Grand9K Pixel Font";
  public static final int SPACE_WIDTH = 2; // two pixels

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public int getSpaceWidth() {
    return SPACE_WIDTH;
  }

  public Grand9K() throws InvalidGlyphSizeException {
    addGlyph( new Glyph( 'A', 5, 7, new byte[] {
      (byte) 0b01111110,
      (byte) 0b00010001,
      (byte) 0b00010001,
      (byte) 0b00010001,
      (byte) 0b01111110
    } ) );
    addGlyph( new Glyph( 'S', 5, 7, new byte[] {
      (byte) 0b01000110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110001
    } ) );

    addGlyph( new Glyph( 'a', 4, 7, new byte[] {
      (byte) 0b00100000,
      (byte) 0b01010100,
      (byte) 0b01010100,
      (byte) 0b01111000
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

    addGlyph( new Glyph( '0', 5, 7, new byte[] {
      (byte) 0b00111110,
      (byte) 0b01010001,
      (byte) 0b01001001,
      (byte) 0b01000101,
      (byte) 0b00111110
    } ) );
    addGlyph( new Glyph( '1', 2, 7, new byte[] {
      (byte) 0b00000001,
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( '2', 5, 7, new byte[] {
      (byte) 0b01110001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01000110
    } ) );
    addGlyph( new Glyph( '3', 4, 7, new byte[] {
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110110
    } ) );
    addGlyph( new Glyph( '4', 5, 7, new byte[] {
      (byte) 0b00011000,
      (byte) 0b00010100,
      (byte) 0b00010010,
      (byte) 0b00010001,
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( '5', 5, 7, new byte[] {
      (byte) 0b01001111,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110001
    } ) );
    addGlyph( new Glyph( '6', 5, 7, new byte[] {
      (byte) 0b00111110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110000
    } ) );
    addGlyph( new Glyph( '7', 5, 7, new byte[] {
      (byte) 0b00000001,
      (byte) 0b00000001,
      (byte) 0b01110001,
      (byte) 0b00001101,
      (byte) 0b00000011
    } ) );
    addGlyph( new Glyph( '8', 5, 7, new byte[] {
      (byte) 0b00110110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110110
    } ) );
    addGlyph( new Glyph( '9', 5, 7, new byte[] {
      (byte) 0b00000110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00111110
    } ) );

    addGlyph( new Glyph( '.', 1, 7, new byte[] {
      (byte) 0b01000000
    } ) );
    addGlyph( new Glyph( ':', 1, 7, new byte[] {
      (byte) 0b01000100
    } ) );
    addGlyph( new Glyph( '-', 3, 7, new byte[] {
      (byte) 0b00010000,
      (byte) 0b00010000,
      (byte) 0b00010000
    } ) );
    addGlyph( new Glyph( 'º', 3, 7, new byte[] {
      (byte) 0b00000110,
      (byte) 0b00001001,
      (byte) 0b00000110
    } ) );
  }
}
