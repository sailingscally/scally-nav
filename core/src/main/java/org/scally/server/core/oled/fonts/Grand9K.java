package org.scally.server.core.oled.fonts;

import org.scally.server.core.oled.Font;
import org.scally.server.core.oled.Glyph;
import org.scally.server.core.oled.InvalidGlyphSizeException;

public class Grand9K extends Font {

  public static final String NAME = "Grand9K Pixel Font";

  public static final int HEIGHT = 8; // number of pixels
  public static final int SPACE_WIDTH = 2; // two pixels

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public int getHeight() {
    return HEIGHT;
  }

  @Override
  public int getSpaceWidth() {
    return SPACE_WIDTH;
  }

  public Grand9K() throws InvalidGlyphSizeException {
    addGlyph( new Glyph( 'A', 5, new byte[] {
      (byte) 0b01111110,
      (byte) 0b00010001,
      (byte) 0b00010001,
      (byte) 0b00010001,
      (byte) 0b01111110
    } ) );
    addGlyph( new Glyph( 'N', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b00000010,
      (byte) 0000000100,
      (byte) 0b00001000,
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( 'S', 5, new byte[] {
      (byte) 0b01000110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110001
    } ) );

    addGlyph( new Glyph( 'a', 4, new byte[] {
      (byte) 0b00100000,
      (byte) 0b01010100,
      (byte) 0b01010100,
      (byte) 0b01111000
    } ) );
    addGlyph( new Glyph( 'c', 3, new byte[] {
      (byte) 0b00111000,
      (byte) 0b01000100,
      (byte) 0b01000100
    } ) );
    addGlyph( new Glyph( 'e', 4, new byte[] {
      (byte) 0b00111000,
      (byte) 0b01010100,
      (byte) 0b01010100,
      (byte) 0b00011000
    } ) );
    addGlyph( new Glyph( 'g', 4, new byte[] {
      (byte) 0b10011000,
      (byte) 0b10100100,
      (byte) 0b10100100,
      (byte) 0b01111100
    } ) );
    addGlyph( new Glyph( 'i', 1, new byte[] {
      (byte) 0b01111010
    } ) );
    addGlyph( new Glyph( 'l', 1, new byte[] {
      (byte) 0b01111110
    } ) );
    addGlyph( new Glyph( 'n', 4, new byte[] {
      (byte) 0b01111100,
      (byte) 0b00000100,
      (byte) 0b00000100,
      (byte) 0b01111000
    } ) );
    addGlyph( new Glyph( 'o', 4, new byte[] {
      (byte) 0b00111000,
      (byte) 0b01000100,
      (byte) 0b01000100,
      (byte) 0b00111000
    } ) );
    addGlyph( new Glyph( 'r', 4, new byte[] {
      (byte) 0b01111100,
      (byte) 0b00001000,
      (byte) 0b00000100,
      (byte) 0b00000100
    } ) );
    addGlyph( new Glyph( 't', 4, new byte[] {
      (byte) 0b00000100,
      (byte) 0b00111111,
      (byte) 0b01000100,
      (byte) 0b01000100
    } ) );
    addGlyph( new Glyph( 'v', 5, new byte[] {
      (byte) 0b00011100,
      (byte) 0b00100000,
      (byte) 0b01000000,
      (byte) 0b00100000,
      (byte) 0b00011100
    } ) );
    addGlyph( new Glyph( 'y', 4, new byte[] {
      (byte) 0b10011100,
      (byte) 0b10100000,
      (byte) 0b10100000,
      (byte) 0b01111100
    } ) );

    addGlyph( new Glyph( '0', 5, new byte[] {
      (byte) 0b00111110,
      (byte) 0b01010001,
      (byte) 0b01001001,
      (byte) 0b01000101,
      (byte) 0b00111110
    } ) );
    addGlyph( new Glyph( '1', 2, new byte[] {
      (byte) 0b00000001,
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( '2', 5, new byte[] {
      (byte) 0b01110001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01000110
    } ) );
    addGlyph( new Glyph( '3', 4, new byte[] {
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110110
    } ) );
    addGlyph( new Glyph( '4', 5, new byte[] {
      (byte) 0b00011000,
      (byte) 0b00010100,
      (byte) 0b00010010,
      (byte) 0b00010001,
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( '5', 5, new byte[] {
      (byte) 0b01001111,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110001
    } ) );
    addGlyph( new Glyph( '6', 5, new byte[] {
      (byte) 0b00111110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110000
    } ) );
    addGlyph( new Glyph( '7', 5, new byte[] {
      (byte) 0b00000001,
      (byte) 0b00000001,
      (byte) 0b01110001,
      (byte) 0b00001101,
      (byte) 0b00000011
    } ) );
    addGlyph( new Glyph( '8', 5, new byte[] {
      (byte) 0b00110110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110110
    } ) );
    addGlyph( new Glyph( '9', 5, new byte[] {
      (byte) 0b00000110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00111110
    } ) );

    addGlyph( new Glyph( '.', 1, new byte[] {
      (byte) 0b01000000
    } ) );
    addGlyph( new Glyph( ':', 1, new byte[] {
      (byte) 0b01000100
    } ) );
    addGlyph( new Glyph( '-', 3, new byte[] {
      (byte) 0b00010000,
      (byte) 0b00010000,
      (byte) 0b00010000
    } ) );
    addGlyph( new Glyph( 'º', 3, new byte[] {
      (byte) 0b00000110,
      (byte) 0b00001001,
      (byte) 0b00000110
    } ) );
  }
}
