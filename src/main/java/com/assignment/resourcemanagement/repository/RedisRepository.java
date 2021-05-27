package com.assignment.resourcemanagement.repository;

import com.assignment.resourcemanagement.domain.Caterer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RedisRepository {
  void save(String key, String cityName, List<Caterer> catererList);

  Page<Caterer> findByCityName(String key, String cityName, Pageable pageable);

  List<Caterer> findByCityName(String key, String cityName);

  // Caterer findById(String key, String id);
}
