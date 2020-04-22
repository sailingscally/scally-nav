package org.scally.server.core.oled;

import org.scally.server.core.oled.fonts.Grand9K;

import java.util.HashMap;
import java.util.Map;

public class FontFactory {

  private static Map<String, Font> fonts = new HashMap<>();

  static {
    try {
      fonts.put( Grand9K.NAME, new Grand9K() );
    } catch ( InvalidGlyphSizeException e ) {
      // TODO: handle invalid glyph size exceptions
      e.printStackTrace();
    }
  }

  public static Font getFont( String name ) throws FontNotFoundException {
    if( !fonts.containsKey( name ) ) {
      throw new FontNotFoundException( name );
    }

    return fonts.get( name );
  }
}
