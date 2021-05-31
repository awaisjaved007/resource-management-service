package com.assignment.resourcemanagement.api.reqs;

import com.assignment.resourcemanagement.boundaries.Contact;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactModel implements Contact {
  private String phoneNumber;

  @Size(min = 5, max = 20, message = "message.request.body.contact.mobileNumber.length.invalid")
  @NotNull(message = "message.request.body.contact.mobileNumber.not.null")
  private String mobileNumber;

  @NotNull(message = "message.request.body.contact.emailAddress.not.null")
  @Pattern(
      regexp = "^.+@.+\\..+$",
      message = "message.request.body.contact.emailAddress.pattern.invalid")
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
