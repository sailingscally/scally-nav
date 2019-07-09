package org.scally.server.core.sensors.adc;

/**
 * The programmable gain amplifier (PGA) is configured by three bits in the config register.
 *
 * A gain of 2/3 allows input measurement to extend up to the supply voltage when VDD is larger than 4V. Although in
 * this case, as well as for PGA = 1 and VDD < 4V, it is not possible to reach a full-scale output code on the ADC.
 */
public enum Gain {

  TWO_THIRDS( 2.0 / 3.0, (byte) 0b00000000, 6.144 ), // default, input range of +/- 6.144V
  ONE( 1.0, (byte) 0b00000010, 4.096 ), // input range of +/- 4.096V
  TWO( 2.0, (byte) 0b00000100, 2.048 ), // input range of +/- 2.048V
  FOUR( 4.0, (byte) 0b00000110, 1.024 ), // input range of +/- 1.024V
  EIGHT( 8.0, (byte) 0b00001000, 0.512 ), // input range of +/- 0.512V
  SIXTEEN( 16.0, (byte) 0b00001010, 0.256 ); // input range of +/- 0.256V

  private double value;
  private byte gain;
  private double fs; // full scale, used to calculate the true voltage from the raw ADC value

  Gain( double value, byte gain, double fs ) {
    this.value = value;
    this.gain = gain;
    this.fs = fs;
  }

  public double getValue() {
    return value;
  }

  public byte getGain() {
    return gain;
  }

  public double getFullScale() {
    return fs;
  }
}
