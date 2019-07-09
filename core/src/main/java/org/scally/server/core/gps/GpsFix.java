package org.scally.server.core.gps;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum GpsFix {

  @JsonProperty( "none" )
  NONE,
  @JsonProperty( "2D" )
  FIX_2D,
  @JsonProperty( "3D" )
  FIX_3D;

}
