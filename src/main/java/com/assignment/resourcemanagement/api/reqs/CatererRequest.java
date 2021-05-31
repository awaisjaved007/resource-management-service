package com.assignment.resourcemanagement.api.reqs;

import com.assignment.resourcemanagement.boundaries.Caterer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatererRequest implements Caterer {

  @Size(min = 1, max = 255, message = "message.request.body.name.length")
  @NotNull(message = "message.request.body.name.not.null")
  private String name;

  @NotNull(message = "message.request.body.capacity.not.null")
  private @Valid CapacityModel capacity;

  @NotNull(message = "message.request.body.address.not.null")
  private @Valid AddressImpl address;

  @NotNull(message = "message.request.body.contact.not.null")
  private @Valid ContactModel contact;

  @Override
  public String toString() {
    return "CatererRequest{"
        + "name='"
        + name
        + '\''
        + ", capacity="
        + capacity
        + ", address="
        + address
        + ", contact="
        + contact
        + '}';
  }
}
