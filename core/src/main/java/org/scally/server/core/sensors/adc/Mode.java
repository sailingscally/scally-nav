package org.scally.server.core.sensors.adc;

public enum Mode {

  CONTINUOUS( (byte) 0b0 ),
  SINGLE( (byte) 0b1 );

  private byte mode;

  Mode( byte mode ) {
    this.mode = mode;
  }

  public byte getValue() {
    return mode;
  }
}
