package com.assignment.resourcemanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseModel {

  private String resultDescription = "";

  public ResponseModel(String resultDescription) {
    this.resultDescription = resultDescription;
  }
}
