package com.assignment.resourcemanagement.mongo.docs;

import com.assignment.resourcemanagement.boundaries.Capacity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class CapacityDocument implements Capacity
{
  @NotNull(message = "message.request.body.capacityDocument.minGuests.not.null")
  @PositiveOrZero(message = "message.request.body.capacityDocument.minGuests.min.value")
  private int minGuests;

  @NotNull(message = "message.request.body.capacityDocument.maxGuests.not.null")
  @PositiveOrZero(message = "message.request.body.capacityDocument.maxGuests.min.value")
  private int maxGuests;
}
