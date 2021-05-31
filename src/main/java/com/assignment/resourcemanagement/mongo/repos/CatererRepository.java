package com.assignment.resourcemanagement.mongo.repos;

import com.assignment.resourcemanagement.mongo.docs.CatererDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CatererRepository extends MongoRepository<CatererDocument, String> {
  Page<CatererDocument> findAllByAddress_CityIgnoreCase(String city, Pageable pageable);
  Optional<CatererDocument> findByNameIgnoreCase(final String name);
}
