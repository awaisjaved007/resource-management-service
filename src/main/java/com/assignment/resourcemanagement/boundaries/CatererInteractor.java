package com.assignment.resourcemanagement.boundaries;

import org.springframework.data.domain.Page;

public interface CatererInteractor {

  void save(Caterer catererModel);

  Page<? extends PersistedCaterer> getCaterersByCityName(String cityName, int page, int size);

  PersistedCaterer getCatererByName(String name);
}
