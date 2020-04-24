package org.scally.server.core.oled;

import org.junit.Test;
import org.scally.server.core.oled.fonts.Grand9K;

public class FontFactoryTest {

  @Test( expected = FontNotFoundException.class )
  public void fontNotFound() throws FontNotFoundException {
    FontFactory.getFont( "InvalidFont" );
  }
}
