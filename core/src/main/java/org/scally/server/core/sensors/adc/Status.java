package org.scally.server.core.sensors.adc;

public enum  Status {

  MEASURING( (byte) 0b00000000 ),
  IDLE( (byte) 0b10000000 );

  private byte status;

  Status( byte status ) {
    this.status = status;
  }

  public byte getValue() {
    return status;
  }
}
