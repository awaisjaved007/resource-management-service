package com.assignment.resourcemanagement.boundaries;

import java.io.Serializable;

public interface Capacity extends Serializable
{
  int getMinGuests();

  int getMaxGuests();
}
