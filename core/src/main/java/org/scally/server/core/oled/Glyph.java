package org.scally.server.core.oled;

public class Glyph {

  private char c;
  private int width;
  private byte[] data;

  public Glyph( char c, int width, byte[] data ) {
    this.c = c;
    this.width = width;
    this.data = data;
  }

  public char getChar() {
    return c;
  }

  public int getWidth() {
    return width;
  }

  public byte[] getData() {
    return data;
  }
}
