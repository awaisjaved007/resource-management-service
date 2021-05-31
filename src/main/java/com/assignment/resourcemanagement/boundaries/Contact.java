package com.assignment.resourcemanagement.boundaries;

import java.io.Serializable;

public interface Contact extends Serializable {
  String getPhoneNumber();

  String getMobileNumber();

  String getEmail();
}
