package org.scally.server.core.oled;

public class GlyphNotFoundException extends Exception {

  public GlyphNotFoundException( char c, String font ) {
    super( String.format( "The glyph for character '%s' wasn't found in font '%s'.", c, font ) );
  }
}
