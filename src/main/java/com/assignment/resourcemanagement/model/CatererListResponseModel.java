package com.assignment.resourcemanagement.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

@Data
@EqualsAndHashCode(callSuper = false)
public class CatererListResponseModel extends ResponseModel implements IModel {

  private Page<CatererModel> caterers;

  public CatererListResponseModel(Page<CatererModel> models, String message) {
    super(message);
    this.caterers = models;
  }

  @Override
  public String toString() {
    return "CatererResponseModel{" + "caterer=" + caterers + '}';
  }
}
