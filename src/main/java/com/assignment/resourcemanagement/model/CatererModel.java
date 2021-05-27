package com.assignment.resourcemanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatererModel extends RepresentationModel implements IModel {

  private String id;

  private String name;

  private CapacityModel capacity;

  private LocationModel location;

  private ContactModel contact;

  @Data
  public static class CapacityModel {

    private Integer minGuests;
    private Integer maxGuests;

    @Override
    public String toString() {
      return "CapacityModel{" + "minGuests=" + minGuests + ", maxGuests=" + maxGuests + '}';
    }
  }

  @Data
  public static class ContactModel {
    private String phoneNumber;

    private String mobileNumber;

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
  public static class LocationModel {

    private String cityName;
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
        + "id='"
        + id
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
