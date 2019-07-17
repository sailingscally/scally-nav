package org.scally.server.core.sensors.adc;

public enum Pointer {

  CONVERSION_REGISTER( 0b00 ),
  CONFIG_REGISTER( 0b01 ),
  LOW_THRESHOLD_REGISTER( 0b10 ),
  HIGH_THRESHOLD_REGISTER( 0b11 );

  private int address;

  Pointer( int address ) {
    this.address = address;
  }

  public int getAddress() {
    return address;
  }
}
