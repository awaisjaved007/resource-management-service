package com.assignment.resourcemanagement.domain;

import com.assignment.resourcemanagement.boundaries.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddressDocument implements Address {
  @NotNull(message = "message.request.body.location.cityName.not.null")
  @Size(min = 1, max = 255, message = "message.request.body.location.cityName.length")
  private String city;

  @NotNull(message = "message.request.body.location.streetNameNumber.not.null")
  @Size(min = 1, max = 255, message = "message.request.body.location.streetNameNumber.length")
  private String street;

  private String postalCode;
}
