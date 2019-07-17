package org.scally.spikes;

public class Enums {

  public enum E1 {
    ZERO( (byte) 0b00000000 ),
    ONE( (byte) 0b00000001 ),
    TWO( (byte) 0b00000010 );

    private byte value;

    E1( byte value ) {
      this.value = value;
    }
  }

  public static void main(String[] args) {
    E1 e1 = E1.ZERO;

    byte b1 = (byte) ( E1.ZERO.value | E1.ONE.value | E1.TWO.value );

    System.out.format( "Data: 0b%s\n", Integer.toBinaryString( b1 ) );
    System.out.format( "E1: 0x%s\n", Integer.toHexString( e1.value ) );
  }

}
