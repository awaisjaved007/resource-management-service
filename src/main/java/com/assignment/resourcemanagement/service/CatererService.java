package com.assignment.resourcemanagement.service;

import com.assignment.resourcemanagement.domain.Caterer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatererService {
  Caterer save(Caterer caterer);

  Caterer getCatererByNameOrId(String nameOrId);

  Page<Caterer> getCaterersByCityName(String cityName, Pageable pageable);
}
