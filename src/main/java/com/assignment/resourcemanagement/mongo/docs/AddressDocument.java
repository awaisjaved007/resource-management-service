package com.assignment.resourcemanagement.mongo.docs;

import com.assignment.resourcemanagement.boundaries.Address;
import lombok.Data;

@Data
public class AddressDocument implements Address {
  private String city;

  private String street;

  private String postalCode;

  @Override
  public String toString()
  {
    return "AddressDocument{" + "city='" + city + '\'' + ", street='" + street + '\'' + ", postalCode='" + postalCode + '\'' + '}';
  }
}
