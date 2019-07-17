package org.scally.server.core.sensors.adc;

public enum  Differential {

  ZERO_ONE( (byte) 0b00000000 ), // default, channel 0 is the positive
  TWO_THREE( (byte) 0b00110000 ); // channel 2 is the positive

  private byte channels;

  Differential( byte channels ) {
    this.channels = channels;
  }

  public byte getValue() {
    return channels;
  }
}
