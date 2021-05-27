package com.assignment.resourcemanagement.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Data
public class Capacity implements Serializable {
  @NotNull(message = "message.request.body.capacity.minGuests.not.null")
  @PositiveOrZero(message = "message.request.body.capacity.minGuests.min.value")
  private int minGuests;

  @NotNull(message = "message.request.body.capacity.maxGuests.not.null")
  @PositiveOrZero(message = "message.request.body.capacity.maxGuests.min.value")
  private int maxGuests;
}
