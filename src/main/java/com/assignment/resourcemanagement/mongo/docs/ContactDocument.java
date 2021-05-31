package com.assignment.resourcemanagement.mongo.docs;

import com.assignment.resourcemanagement.boundaries.Contact;
import lombok.Data;

@Data
public class ContactDocument implements Contact {

  private String phoneNumber;

  private String mobileNumber;

  private String email;
}
