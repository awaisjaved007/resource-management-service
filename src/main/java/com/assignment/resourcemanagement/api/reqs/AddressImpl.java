package com.assignment.resourcemanagement.api.reqs;

import com.assignment.resourcemanagement.boundaries.Address;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressImpl implements Address {

  @NotNull(message = "message.request.body.address.cityName.not.null")
  @Size(min = 1, max = 255, message = "message.request.body.address.cityName.length")
  private String city;

  @NotNull(message = "message.request.body.address.streetNameNumber.not.null")
  @Size(min = 1, max = 255, message = "message.request.body.address.streetNameNumber.length")
  private String street;

  private String postalCode;

  @Override
  public String toString() {
    return "AddressImpl{"
        + "cityName='"
        + city
        + '\''
        + ", streetNameNumber='"
        + street
        + +'\''
        + ", postalCode='"
        + postalCode
        + '\''
        + '}';
  }
}
