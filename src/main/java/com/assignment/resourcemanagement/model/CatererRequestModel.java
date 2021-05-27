package com.assignment.resourcemanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatererRequestModel implements IModel {

  @Size(min = 1, max = 255, message = "message.request.body.name.length")
  @NotNull(message = "message.request.body.name.not.null")
  private String name;

  @NotNull(message = "message.request.body.capacity.not.null")
  private @Valid CapacityModel capacity;

  @NotNull(message = "message.request.body.location.not.null")
  private @Valid LocationModel location;

  @NotNull(message = "message.request.body.contact.not.null")
  private @Valid ContactModel contact;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CapacityModel {

    @NotNull(message = "message.request.body.capacity.minGuests.not.null")
    @Positive(message = "message.request.body.capacity.minGuests.min.value")
    private Integer minGuests;

    @NotNull(message = "message.request.body.capacity.maxGuests.not.null")
    @Positive(message = "message.request.body.capacity.maxGuests.min.value")
    private Integer maxGuests;

    @Override
    public String toString() {
      return "CapacityModel{" + "minGuests=" + minGuests + ", maxGuests=" + maxGuests + '}';
    }
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ContactModel {
    private String phoneNumber;

    @Size(min = 5, max = 20, message = "message.request.body.contact.mobileNumber.length.invalid")
    @NotNull(message = "message.request.body.contact.mobileNumber.not.null")
    private String mobileNumber;

    @NotNull(message = "message.request.body.contact.emailAddress.not.null")
    @Pattern(
        regexp = "^.+@.+\\..+$",
        message = "message.request.body.contact.emailAddress.pattern.invalid")
    private String emailAddress;

    @Override
    public String toString() {
      return "ContactModel{"
          + "phoneNumber='"
          + phoneNumber
          + '\''
          + ", mobileNumber='"
          + mobileNumber
          + '\''
          + ", emailAddress='"
          + emailAddress
          + '\''
          + '}';
    }
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class LocationModel {

    @NotNull(message = "message.request.body.location.cityName.not.null")
    @Size(min = 1, max = 255, message = "message.request.body.location.cityName.length")
    private String cityName;

    @NotNull(message = "message.request.body.location.streetNameNumber.not.null")
    @Size(min = 1, max = 255, message = "message.request.body.location.streetNameNumber.length")
    private String streetNameNumber;

    private String postalCode;

    @Override
    public String toString() {
      return "LocationModel{"
          + "cityName='"
          + cityName
          + '\''
          + ", streetNameNumber='"
          + streetNameNumber
          + '\''
          + ", postalCode='"
          + postalCode
          + '\''
          + '}';
    }
  }

  @Override
  public String toString() {
    return "CatererRequestModel{"
        + "name='"
        + name
        + '\''
        + ", capacity="
        + capacity
        + ", location="
        + location
        + ", contact="
        + contact
        + '}';
  }
}
