package com.assignment.resourcemanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CatererResponseModel extends ResponseModel {

  private CatererModel caterer;

  public CatererResponseModel(String msg, CatererModel caterer) {
    super(msg);
    this.caterer = caterer;
  }

  @Override
  public String toString() {
    return "CatererResponseModel{" + "caterer=" + caterer + '}';
  }
}
