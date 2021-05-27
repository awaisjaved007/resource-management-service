package com.assignment.resourcemanagement.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class Location implements Serializable {
  @NotNull(message = "message.request.body.location.cityName.not.null")
  @Size(min = 1, max = 255, message = "message.request.body.location.cityName.length")
  private String cityName;

  @NotNull(message = "message.request.body.location.streetNameNumber.not.null")
  @Size(min = 1, max = 255, message = "message.request.body.location.streetNameNumber.length")
  private String streetNameNumber;

  private String postalCode;
}
