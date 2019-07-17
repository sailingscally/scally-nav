package org.scally.server.core.sensors.adc;

public enum Channel {

  ZERO( (byte) 0b01000000 ),
  ONE( (byte) 0b01010000 ),
  TWO( (byte) 0b01100000 ),
  THREE( (byte) 0b01110000 );

  private byte channel;

  Channel( byte channel ) {
    this.channel = channel;
  }

  public byte getValue() {
    return channel;
  }
}
