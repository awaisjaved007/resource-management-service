package com.assignment.resourcemanagement.boundaries;

import java.io.Serializable;

public interface Caterer extends Serializable {
  String getName();

  Capacity getCapacity();

  Contact getContact();

  Address getAddress();
}
