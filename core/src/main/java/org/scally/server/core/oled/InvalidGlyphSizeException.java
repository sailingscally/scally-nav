package org.scally.server.core.oled;

public class InvalidGlyphSizeException extends Exception {

  public InvalidGlyphSizeException( char c, String font ) {
    super( String.format( "The Glyph for character '%s' in font '%s' has an invalid specified size.", c, font ) );
  }
}
