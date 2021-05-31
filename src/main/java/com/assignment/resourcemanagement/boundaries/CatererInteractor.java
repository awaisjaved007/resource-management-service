package com.assignment.resourcemanagement.boundaries;

import com.assignment.resourcemanagement.mongo.docs.CatererDocument;
import org.springframework.data.domain.Page;

public interface CatererInteractor {

  CatererDocument save(Caterer catererModel);

  Page<? extends PersistedCaterer> getCaterersByCityName(String cityName, int page, int size);

  PersistedCaterer getCatererByName(String name);
}
