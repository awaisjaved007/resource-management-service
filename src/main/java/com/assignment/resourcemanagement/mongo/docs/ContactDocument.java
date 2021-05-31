package com.assignment.resourcemanagement.domain;

import com.assignment.resourcemanagement.boundaries.Contact;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ContactDocument implements Contact {

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
}
