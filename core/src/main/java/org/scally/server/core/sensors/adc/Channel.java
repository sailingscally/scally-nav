package org.scally.server.core.sensors.adc;

public enum Channel {

  ZERO( (byte) 0b01000000 ), // single ended channels
  ONE( (byte) 0b01010000 ),
  TWO( (byte) 0b01100000 ),
  THREE( (byte) 0b01110000 ),
  ZERO_ONE( (byte) 0b00000000 ), // (default) differential channels , channel 0 is the positive
  TWO_THREE( (byte) 0b00110000 ); // channel 2 is the positive

  private byte channel;

  Channel( byte channel ) {
    this.channel = channel;
  }

  public byte getValue() {
    return channel;
  }
}
