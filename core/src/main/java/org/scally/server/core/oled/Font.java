package org.scally.server.core.oled;

import java.util.HashMap;
import java.util.Map;

public abstract class Font {

  private Map<Character, Glyph> glyphs = new HashMap<>();

  public abstract String getName();

  public Glyph getGlyph( char c ) {
    return glyphs.get( c );
  }

  public void addGlyph( Glyph glyph ) {
    glyphs.put( glyph.getChar(), glyph );
  }
}
