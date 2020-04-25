package org.scally.server.services;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class AtmosphericPressure {

  private Instant time;
  private Double value;

  public AtmosphericPressure( Instant time, Double value ) {
    this.time = time;
    this.value = value;
  }

  @JsonProperty( "time" )
  public Instant getTime() {
    return time;
  }

  @JsonProperty( "pressure" )
  public Double getValue() {
    return value;
  }
}
