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
public class CatererRequest implements Caterer {

  @Size(min = 1, max = 255, message = "message.request.body.name.length")
  @NotNull(message = "message.request.body.name.not.null")
  private String name;

  @NotNull(message = "message.request.body.capacityDocument.not.null")
  private @Valid CapacityModel capacity;

  @NotNull(message = "message.request.body.location.not.null")
  private @Valid AddressImpl address;

  @NotNull(message = "message.request.body.contactDocument.not.null")
  private @Valid ContactModel contact;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CapacityModel implements Capacity {

    @NotNull(message = "message.request.body.capacityDocument.minGuests.not.null")
    @Positive(message = "message.request.body.capacityDocument.minGuests.min.value")
    private int minGuests;

    @NotNull(message = "message.request.body.capacityDocument.maxGuests.not.null")
    @Positive(message = "message.request.body.capacityDocument.maxGuests.min.value")
    private int maxGuests;

    @Override
    public String toString() {
      return "CapacityModel{" + "minGuests=" + minGuests + ", maxGuests=" + maxGuests + '}';
    }
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ContactModel implements Contact {
    private String phoneNumber;

    @Size(
        min = 5,
        max = 20,
        message = "message.request.body.contactDocument.mobileNumber.length.invalid")
    @NotNull(message = "message.request.body.contactDocument.mobileNumber.not.null")
    private String mobileNumber;

    @NotNull(message = "message.request.body.contactDocument.emailAddress.not.null")
    @Pattern(
        regexp = "^.+@.+\\..+$",
        message = "message.request.body.contactDocument.emailAddress.pattern.invalid")
    private String email;

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
          + email
          + '\''
          + '}';
    }
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AddressImpl implements Address {

    @NotNull(message = "message.request.body.location.cityName.not.null")
    @Size(min = 1, max = 255, message = "message.request.body.location.cityName.length")
    private String city;

    @NotNull(message = "message.request.body.location.streetNameNumber.not.null")
    @Size(min = 1, max = 255, message = "message.request.body.location.streetNameNumber.length")
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

  @Override
  public String toString() {
    return "CatererRequest{"
        + "name='"
        + name
        + '\''
        + ", capacityDocument="
        + capacity
        + ", address="
        + address
        + ", contactDocument="
        + contact
        + '}';
  }
}
