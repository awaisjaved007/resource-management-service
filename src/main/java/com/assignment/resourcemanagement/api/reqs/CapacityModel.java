package com.assignment.resourcemanagement.api.reqs;

import com.assignment.resourcemanagement.boundaries.Capacity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CapacityModel implements Capacity {

  @NotNull(message = "message.request.body.capacity.minGuests.not.null")
  @Positive(message = "message.request.body.capacity.minGuests.min.value")
  private int minGuests;

  @NotNull(message = "message.request.body.capacity.maxGuests.not.null")
  @Positive(message = "message.request.body.capacity.maxGuests.min.value")
  private int maxGuests;

  @Override
  public String toString() {
    return "CapacityModel{" + "minGuests=" + minGuests + ", maxGuests=" + maxGuests + '}';
  }
}
