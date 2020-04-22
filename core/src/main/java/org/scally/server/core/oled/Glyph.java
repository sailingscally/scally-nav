package org.scally.server.core.oled;

public class Glyph {

  private char c;
  private int width;
  private int height;
  private byte[] data;

  public Glyph( char c, int width, int height, byte[] data ) {
    this.c = c;
    this.width = width;
    this.height = height;
    this.data = data;
  }

  public char getChar() {
    return c;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public byte[] getData() {
    return data;
  }
}
