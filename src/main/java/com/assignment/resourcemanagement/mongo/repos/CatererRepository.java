package com.assignment.resourcemanagement.repository;

import com.assignment.resourcemanagement.domain.CatererDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatererRepository extends MongoRepository<CatererDocument, String> {
  Page<CatererDocument> findAllByAddress_CityIgnoreCase(String city, Pageable pageable);
}
