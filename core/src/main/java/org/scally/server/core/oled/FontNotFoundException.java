package org.scally.server.core.oled;

public class FontNotFoundException extends Exception {

  public FontNotFoundException( String name ) {
    super( String.format( "The font '%s' is not present in this system.", name ) );
  }
}
