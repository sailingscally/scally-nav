package org.scally.server.core.sensors.adc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GainTest {

  @Test
  public void one() {
    assertEquals( 1.0, Gain.ONE.getValue(), 0.0 );
    assertEquals( 0b001, Gain.ONE.getGain() >> 1 );
    assertEquals( 4.096, Gain.ONE.getFullScale(), 0.0 );
  }

  @Test
  public void two() {
    assertEquals( 2.0, Gain.TWO.getValue(), 0.0 );
    assertEquals( 0b010, Gain.TWO.getGain() >> 1 );
    assertEquals( 2.048, Gain.TWO.getFullScale(), 0.0 );
  }

  @Test
  public void four() {
    assertEquals( 4.0, Gain.FOUR.getValue(), 0.0 );
    assertEquals( 0b011, Gain.FOUR.getGain() >> 1 );
    assertEquals( 1.024, Gain.FOUR.getFullScale(), 0.0 );
  }

  @Test
  public void eight() {
    assertEquals( 8.0, Gain.EIGHT.getValue(), 0.0 );
    assertEquals( 0b100, Gain.EIGHT.getGain() >> 1 );
    assertEquals( 0.512, Gain.EIGHT.getFullScale(), 0.0 );
  }

  @Test
  public void sixteen() {
    assertEquals( 16.0, Gain.SIXTEEN.getValue(), 0.0 );
    assertEquals( 0b101, Gain.SIXTEEN.getGain() >> 1 );
    assertEquals( 0.256, Gain.SIXTEEN.getFullScale(), 0.0 );
  }

  @Test
  public void twoThirds() {
    assertEquals( 0.666666, Gain.TWO_THIRDS.getValue(), 0.000001 );
    assertEquals( 0b000, Gain.TWO_THIRDS.getGain() >> 1 );
    assertEquals( 6.144, Gain.TWO_THIRDS.getFullScale(), 0.0 );
  }
}
