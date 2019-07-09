package org.scally.server.core.oled;

import java.util.HashMap;
import java.util.Map;

public abstract class Font {

  private Map<Character, Glyph> glyphs = new HashMap<>();

  public abstract String getName();

  public abstract int getHeight();
  public abstract int getSpaceWidth();

  public Glyph getGlyph( char c ) throws GlyphNotFoundException {
    if( !glyphs.containsKey( c ) ) {
      throw new GlyphNotFoundException( c, getName() );
    }

    return glyphs.get( c );
  }

  protected void addGlyph( Glyph glyph ) throws InvalidGlyphSizeException {
    if( glyph.getWidth() != glyph.getData().length ) {
      throw new InvalidGlyphSizeException( glyph.getChar(), getName() );
    }

    glyphs.put( glyph.getChar(), glyph );
  }

  public int getTextWidth( String text ) throws GlyphNotFoundException {
    int width = ( text.length() - 1 ) * getSpaceWidth();

    for( int i = 0; i < text.length(); i ++ ) {
      width += getGlyph( text.charAt( i ) ).getWidth();
    }

    return width;
  }

  /**
   * Returns the numbers of glyphs in this font.
   */
  public int getLength() {
    return glyphs.size();
  }
}
