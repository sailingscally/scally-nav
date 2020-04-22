package oled.fonts;

import org.junit.Test;
import org.scally.server.core.oled.Font;
import org.scally.server.core.oled.FontFactory;
import org.scally.server.core.oled.FontNotFoundException;
import org.scally.server.core.oled.GlyphNotFoundException;
import org.scally.server.core.oled.fonts.Grand9K;

import static org.junit.Assert.assertEquals;

public class Grand9KTest {

  private static final int LENGTH = 83;

  @Test
  public void getName() throws FontNotFoundException {
    assertEquals( Grand9K.NAME, FontFactory.getFont( Grand9K.NAME ).getName() );
  }

  @Test
  public void getGlyphs() throws FontNotFoundException, GlyphNotFoundException {
    String glyphs = "ABCDEFGHIJKLMNOPRQSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,;:-$#'!\"/?%&()@º \u00D9\u00DA";
    Font font = FontFactory.getFont( Grand9K.NAME );

    // 26 characters in this font
    assertEquals( LENGTH, glyphs.length() );
    assertEquals( LENGTH, font.getLength() );

    for( int i = 0; i < glyphs.length(); i ++ ) {
      assertEquals( glyphs.charAt( i ), font.getGlyph( glyphs.charAt( i ) ).getChar() );
    }
  }
}
