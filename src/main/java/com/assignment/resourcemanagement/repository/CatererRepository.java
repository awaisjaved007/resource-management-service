package com.assignment.resourcemanagement.repository;

import com.assignment.resourcemanagement.domain.Caterer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CatererRepository extends MongoRepository<Caterer, String> {
  Optional<Caterer> findByIdOrNameIgnoreCase(String id, String name);

  Page<Caterer> findAllByLocation_CityNameIgnoreCase(String cityName, Pageable pageable);

  List<Caterer> findAllByLocation_CityNameIgnoreCase(String cityName);
}
