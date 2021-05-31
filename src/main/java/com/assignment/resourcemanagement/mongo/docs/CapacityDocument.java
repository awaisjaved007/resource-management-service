package com.assignment.resourcemanagement.mongo.docs;

import com.assignment.resourcemanagement.boundaries.Capacity;
import lombok.Data;

@Data
public class CapacityDocument implements Capacity {
  private int minGuests;

  private int maxGuests;
}
