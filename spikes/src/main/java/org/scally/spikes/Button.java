package org.scally.spikes;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Button {

  public static void main( String args[] ) throws InterruptedException {
    System.out.println( "Push button example" );

    // create gpio controller
    final GpioController gpio = GpioFactory.getInstance();

    // gpio pin #02 as an input pin with its internal pull down resistor enabled
    final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin( RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN ); // GPIO 17

    // create and register gpio pin listener
    myButton.addListener( new GpioPinListenerDigital() {
      @Override
      public void handleGpioPinDigitalStateChangeEvent( GpioPinDigitalStateChangeEvent event ) {
        // display pin state on console
        System.out.println( " Switch change detected: " + event.getPin() + " = " + event.getState() );
      }

    } );


    // keep program running until user aborts (CTRL-C)
    for ( ; ; ) {
      Thread.sleep( 1500 );
    }
  }
}
