package org.scally.server.core.sensors.adc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModeTest {

  @Test
  public void continuous() {
    assertEquals( 0b0, Mode.CONTINUOUS.getValue() );
  }

  @Test
  public void single() {
    assertEquals( 0b1, Mode.SINGLE.getValue() );
  }
}
