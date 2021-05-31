package com.assignment.resourcemanagement.boundaries;

import org.springframework.data.domain.Page;

public interface CatererInteractor {

  void save(Caterer catererModel);

  PersistedCaterer getCatererById(String Id);

  Page<? extends PersistedCaterer> getCaterersByCityName(String cityName, int page, int size);
}
