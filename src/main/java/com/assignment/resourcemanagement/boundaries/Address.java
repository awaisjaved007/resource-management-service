package com.assignment.resourcemanagement.boundaries;

import java.io.Serializable;

public interface Address extends Serializable
{
  String getStreet();

  String getCity();

  String getPostalCode();
}
