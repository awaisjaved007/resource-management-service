package com.assignment.resourcemanagement.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class Contact implements Serializable {

  private String phoneNumber;

  @Size(min = 5, max = 20, message = "message.request.body.contact.mobileNumber.length.invalid")
  @NotNull(message = "message.request.body.contact.mobileNumber.not.null")
  private String mobileNumber;

  @NotNull(message = "message.request.body.contact.emailAddress.not.null")
  @Pattern(
      regexp = "^.+@.+\\..+$",
      message = "message.request.body.contact.emailAddress.pattern.invalid")
  private String emailAddress;
}
