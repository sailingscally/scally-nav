package org.scally.server.core.sensors.adc;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

public class ADS1015Test {

  private I2CBus bus;
  private I2CDevice device;

  private ADS1015 ads;

  @Before
  public void before() {
    bus = mock( I2CBus.class );
    device = mock( I2CDevice.class );

    ads = mock( ADS1015.class );

    setInternalState( ads, "bus", bus );
    setInternalState( ads, "device", device );
    setInternalState( ads, "lock", new ReentrantLock() );
  }

  @Test
  public void readSingleEndedChannelOneWithDefaultGain() throws InterruptedException, IOException {
    doCallRealMethod().when( ads ).readSingleEnded( any( Channel.class ), any( Gain.class ) );

    ads.readSingleEnded( Channel.ONE, Gain.TWO_THIRDS );
    verify( device, times( 1 ) ).write( (byte) 0b01, new byte[] { (byte) 0b11010001, (byte) 0b00000011 }, 0, 2 );
    verify( device, times( 1 ) ).read( anyInt(), any( byte[].class ), anyInt(), anyInt() );
  }

  @Test
  public void readSingleEndedChannelThreeWith2XGain() throws InterruptedException, IOException {
    doCallRealMethod().when( ads ).readSingleEnded( any( Channel.class ), any( Gain.class ) );

    ads.readSingleEnded( Channel.THREE, Gain.TWO );
    verify( device, times( 1 ) ).write( (byte) 0b01, new byte[] { (byte) 0b11110101, (byte) 0b00000011 }, 0, 2 );
    verify( device, times( 1 ) ).read( anyInt(), any( byte[].class ), anyInt(), anyInt() );
  }
}
