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
    addGlyph( new Glyph( 'B', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110110
    } ) );
    addGlyph( new Glyph( 'C', 4, new byte[] {
      (byte) 0b00111110,
      (byte) 0b01000001,
      (byte) 0b01000001,
      (byte) 0b01000001
    } ) );
    addGlyph( new Glyph( 'D', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b01000001,
      (byte) 0b01000001,
      (byte) 0b01000001,
      (byte) 0b00111110
    } ) );
    addGlyph( new Glyph( 'E', 4, new byte[] {
      (byte) 0b01111111,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001
    } ) );
    addGlyph( new Glyph( 'F', 4, new byte[] {
      (byte) 0b01111111,
      (byte) 0b00001001,
      (byte) 0b00001001,
      (byte) 0b00001001
    } ) );
    addGlyph( new Glyph( 'G', 5, new byte[] {
      (byte) 0b00111110,
      (byte) 0b01000001,
      (byte) 0b01000001,
      (byte) 0b01001001,
      (byte) 0b01111001
    } ) );
    addGlyph( new Glyph( 'H', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b00001000,
      (byte) 0b00001000,
      (byte) 0b00001000,
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( 'I', 1, new byte[] {
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( 'J', 5, new byte[] {
      (byte) 0b01000000,
      (byte) 0b01000000,
      (byte) 0b01000000,
      (byte) 0b01000000,
      (byte) 0b00111111
    } ) );
    addGlyph( new Glyph( 'K', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b00001000,
      (byte) 0b00010100,
      (byte) 0b00100010,
      (byte) 0b01000001
    } ) );
    addGlyph( new Glyph( 'L', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b01000000,
      (byte) 0b01000000,
      (byte) 0b01000000,
      (byte) 0b01000000
    } ) );
    addGlyph( new Glyph( 'M', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b00000010,
      (byte) 0b00000100,
      (byte) 0b00000010,
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( 'N', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b00000010,
      (byte) 0000000100,
      (byte) 0b00001000,
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( 'O', 5, new byte[] {
      (byte) 0b00111110,
      (byte) 0b01000001,
      (byte) 0b01000001,
      (byte) 0b01000001,
      (byte) 0b00111110,
    } ) );
    addGlyph( new Glyph( 'P', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b00010001,
      (byte) 0b00010001,
      (byte) 0b00010001,
      (byte) 0b00001110
    } ) );
    addGlyph( new Glyph( 'Q', 6, new byte[] {
      (byte) 0b00111110,
      (byte) 0b01000001,
      (byte) 0b01000001,
      (byte) 0b01000001,
      (byte) 0b10111110,
      (byte) 0b10000000
    } ) );
    addGlyph( new Glyph( 'R', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b00010001,
      (byte) 0b00010001,
      (byte) 0b00010001,
      (byte) 0b01101110
    } ) );
    addGlyph( new Glyph( 'S', 5, new byte[] {
      (byte) 0b01000110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00110001
    } ) );
    addGlyph( new Glyph( 'T', 5, new byte[] {
      (byte) 0b00000001,
      (byte) 0b00000001,
      (byte) 0b01111111,
      (byte) 0b00000001,
      (byte) 0b00000001
    } ) );
    addGlyph( new Glyph( 'U', 5, new byte[] {
      (byte) 0b00111111,
      (byte) 0b01000000,
      (byte) 0b01000000,
      (byte) 0b01000000,
      (byte) 0b00111111
    } ) );
    addGlyph( new Glyph( 'V', 5, new byte[] {
      (byte) 0b00011111,
      (byte) 0b00100000,
      (byte) 0b01000000,
      (byte) 0b00100000,
      (byte) 0b00011111,
    } ) );
    addGlyph( new Glyph( 'W', 5, new byte[] {
      (byte) 0b01111111,
      (byte) 0b00100000,
      (byte) 0b00010000,
      (byte) 0b00100000,
      (byte) 0b01111111
    } ) );
    addGlyph( new Glyph( 'X', 5, new byte[] {
      (byte) 0b01100011,
      (byte) 0b00010100,
      (byte) 0b00001000,
      (byte) 0b00010100,
      (byte) 0b01100011,
    } ) );
    addGlyph( new Glyph( 'Y', 5, new byte[] {
      (byte) 0b00000111,
      (byte) 0b00001000,
      (byte) 0b01111000,
      (byte) 0b00001000,
      (byte) 0b00000111
    } ) );
    addGlyph( new Glyph( 'Z', 5, new byte[] {
      (byte) 0b01100001,
      (byte) 0b01010001,
      (byte) 0b01001001,
      (byte) 0b01000101,
      (byte) 0b01000011
    } ) );

    addGlyph( new Glyph( 'a', 4, new byte[] {
      (byte) 0b00100000,
      (byte) 0b01010100,
      (byte) 0b01010100,
      (byte) 0b01111000
    } ) );
    addGlyph( new Glyph( 'b', 4, new byte[] {
      (byte) 0b01111110,
      (byte) 0b01001000,
      (byte) 0b01001000,
      (byte) 0b00110000
    } ) );
    addGlyph( new Glyph( 'c', 3, new byte[] {
      (byte) 0b00111000,
      (byte) 0b01000100,
      (byte) 0b01000100
    } ) );
    addGlyph( new Glyph( 'd', 4, new byte[] {
      (byte) 0b00110000,
      (byte) 0b01001000,
      (byte) 0b01001000,
      (byte) 0b01111110
    } ) );
    addGlyph( new Glyph( 'e', 4, new byte[] {
      (byte) 0b00111000,
      (byte) 0b01010100,
      (byte) 0b01010100,
      (byte) 0b00011000
    } ) );
    addGlyph( new Glyph( 'f', 4, new byte[] {
      (byte) 0b00001000,
      (byte) 0b01111100,
      (byte) 0b00001010,
      (byte) 0b00001010
    } ) );
    addGlyph( new Glyph( 'g', 4, new byte[] {
      (byte) 0b10011000,
      (byte) 0b10100100,
      (byte) 0b10100100,
      (byte) 0b01111100
    } ) );
    addGlyph( new Glyph( 'h', 4, new byte[] {
      (byte) 0b01111110,
      (byte) 0b00001000,
      (byte) 0b00001000,
      (byte) 0b01110000
    } ) );
    addGlyph( new Glyph( 'i', 1, new byte[] {
      (byte) 0b01111010
    } ) );
    addGlyph( new Glyph( 'j', 3, new byte[] {
      (byte) 0b10000000,
      (byte) 0b10000000,
      (byte) 0b01111010
    } ) );
    addGlyph( new Glyph( 'k', 4, new byte[] {
      (byte) 0b01111110,
      (byte) 0b00010000,
      (byte) 0b00101000,
      (byte) 0b01000100
    } ) );
    addGlyph( new Glyph( 'l', 1, new byte[] {
      (byte) 0b01111110
    } ) );
    addGlyph( new Glyph( 'm', 5, new byte[] {
      (byte) 0b01111100,
      (byte) 0b00000100,
      (byte) 0b00111000,
      (byte) 0b00000100,
      (byte) 0b01111000
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
    addGlyph( new Glyph( 'p', 4, new byte[] {
      (byte) 0b11111100,
      (byte) 0b01000100,
      (byte) 0b01000100,
      (byte) 0b00111000
    } ) );
    addGlyph( new Glyph( 'q', 4, new byte[] {
      (byte) 0b00111000,
      (byte) 0b01000100,
      (byte) 0b01000100,
      (byte) 0b11111100
    } ) );
    addGlyph( new Glyph( 'r', 4, new byte[] {
      (byte) 0b01111100,
      (byte) 0b00001000,
      (byte) 0b00000100,
      (byte) 0b00000100
    } ) );
    addGlyph( new Glyph( 's', 4, new byte[] {
      (byte) 0b01001000,
      (byte) 0b01010100,
      (byte) 0b01010100,
      (byte) 0b00100100
    } ) );
    addGlyph( new Glyph( 't', 4, new byte[] {
      (byte) 0b00000100,
      (byte) 0b00111111,
      (byte) 0b01000100,
      (byte) 0b01000100
    } ) );
    addGlyph( new Glyph( 'u', 4, new byte[] {
      (byte) 0b00111100,
      (byte) 0b01000000,
      (byte) 0b01000000,
      (byte) 0b01111100
    } ) );
    addGlyph( new Glyph( 'v', 5, new byte[] {
      (byte) 0b00011100,
      (byte) 0b00100000,
      (byte) 0b01000000,
      (byte) 0b00100000,
      (byte) 0b00011100
    } ) );
    addGlyph( new Glyph( 'w', 5, new byte[] {
      (byte) 0b00011100,
      (byte) 0b00100000,
      (byte) 0b00111000,
      (byte) 0b00100000,
      (byte) 0b00011100
    } ) );
    addGlyph( new Glyph( 'x', 5, new byte[] {
      (byte) 0b01000100,
      (byte) 0b00101000,
      (byte) 0b00010000,
      (byte) 0b00101000,
      (byte) 0b01000100
    } ) );
    addGlyph( new Glyph( 'y', 4, new byte[] {
      (byte) 0b10011100,
      (byte) 0b10100000,
      (byte) 0b10100000,
      (byte) 0b01111100
    } ) );
    addGlyph( new Glyph( 'z', 5, new byte[] {
      (byte) 0b01000100,
      (byte) 0b01100100,
      (byte) 0b01010100,
      (byte) 0b01001100,
      (byte) 0b01000100
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
    addGlyph( new Glyph( ',', 1, new byte[] {
      (byte) 0b11000000
    } ) );
    addGlyph( new Glyph( ';', 1, new byte[] {
      (byte) 0b11000100
    } ) );
    addGlyph( new Glyph( ':', 1, new byte[] {
      (byte) 0b01000100
    } ) );
    addGlyph( new Glyph( '-', 3, new byte[] {
      (byte) 0b00010000,
      (byte) 0b00010000,
      (byte) 0b00010000
    } ) );
    addGlyph( new Glyph( '$', 5, new byte[] {
      (byte) 0b00100100,
      (byte) 0b00101010,
      (byte) 0b01111111,
      (byte) 0b00101010,
      (byte) 0b00010010
    } ) );
    addGlyph( new Glyph( '#', 6, new byte[] {
      (byte) 0b00100010,
      (byte) 0b01111111,
      (byte) 0b00100010,
      (byte) 0b00100010,
      (byte) 0b01111111,
      (byte) 0b00100010
    } ) );
    addGlyph( new Glyph( '\'', 1, new byte[] {
      (byte) 0b00000011
    } ) );
    addGlyph( new Glyph( '!', 1, new byte[] {
      (byte) 0b01011111
    } ) );
    addGlyph( new Glyph( '"', 3, new byte[] {
      (byte) 0b00000011,
      (byte) 0b00000000,
      (byte) 0b00000011
    } ) );
    addGlyph( new Glyph( '/', 3, new byte[] {
      (byte) 0b01100000,
      (byte) 0b00011100,
      (byte) 0b00000011
    } ) );
    addGlyph( new Glyph( '?', 4, new byte[] {
      (byte) 0b00000001,
      (byte) 0b01010001,
      (byte) 0b00001001,
      (byte) 0b00000110
    } ) );
    addGlyph( new Glyph( '%', 7, new byte[] {
      (byte) 0b01000110,
      (byte) 0b00101001,
      (byte) 0b00010110,
      (byte) 0b00001000,
      (byte) 0b00110100,
      (byte) 0b01001010,
      (byte) 0b00110001
    } ) );
    addGlyph( new Glyph( '&', 5, new byte[] {
      (byte) 0b00110110,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b01001001,
      (byte) 0b00111100
    } ) );
    addGlyph( new Glyph( '(', 3, new byte[] {
      (byte) 0b00111110,
      (byte) 0b01000001,
      (byte) 0b01000001
    } ) );
    addGlyph( new Glyph( ')', 3, new byte[] {
      (byte) 0b01000001,
      (byte) 0b01000001,
      (byte) 0b00111110
    } ) );
    addGlyph( new Glyph( '@', 7, new byte[] {
      (byte) 0b01111110,
      (byte) 0b10000001,
      (byte) 0b10011001,
      (byte) 0b10100101,
      (byte) 0b10111101,
      (byte) 0b10100001,
      (byte) 0b00011110
    } ) );
    addGlyph( new Glyph( 'º', 3, new byte[] {
      (byte) 0b00000110,
      (byte) 0b00001001,
      (byte) 0b00000110
    } ) );
    addGlyph( new Glyph( ' ', 1, new byte[] {
      (byte) 0b00000000
    } ) );

    addGlyph( new Glyph( '\u00D9', 2, new byte[] { // this is a small 1
      (byte) 0b00000100,
      (byte) 0b01111100
    } ) );
    addGlyph( new Glyph( '\u00DA', 3, new byte[] { // this is a small 2
      (byte) 0b01110100,
      (byte) 0b01010100,
      (byte) 0b01011100
    } ) );
  }
}
