package org.scally.server.core.sensors.adc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatusTest {

  @Test
  public void measuring() {
    assertEquals( 0b0, Status.MEASURING.getValue() >> 7 );
  }

  @Test
  public void idle() {
    assertEquals( 0b1, ( Status.IDLE.getValue() & 0xFF ) >> 7 );
  }
}
