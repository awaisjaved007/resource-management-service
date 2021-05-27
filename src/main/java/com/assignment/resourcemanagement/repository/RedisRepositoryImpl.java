package com.assignment.resourcemanagement.repository;

import com.assignment.resourcemanagement.domain.Caterer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisRepositoryImpl.class);

  private final RedisTemplate<String, Caterer> redisTemplate;

  @Autowired
  public RedisRepositoryImpl(final RedisTemplate<String, Caterer> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void save(String key, String cityName, List<Caterer> catererList) {
    this.redisTemplate.opsForHash().put(key, cityName, catererList);
  }

  public List<Caterer>findByCityName(String key, String cityName){
    HashOperations<String, String, List<Caterer>> hashOperations = this.redisTemplate.opsForHash();
    List<Caterer> cachedCaterers = new LinkedList<>();
    if (hashOperations.hasKey(key, cityName)) {
      cachedCaterers =  hashOperations.get(key, cityName);
    }
    return cachedCaterers;
  }

  @Override
  public Page<Caterer> findByCityName(String key, String cityName, Pageable pageable) {
    List<Caterer> cachedCaterers = new LinkedList<>();
    Page<Caterer> caterers = new PageImpl<>(cachedCaterers, pageable, cachedCaterers.size());
    HashOperations<String, String, List<Caterer>> hashOperations = this.redisTemplate.opsForHash();

    if (hashOperations.hasKey(key, cityName)) {

      cachedCaterers = hashOperations.get(key, cityName);

      if (cachedCaterers != null) {
        int start = (int) pageable.getOffset();
        int end =
            (start + pageable.getPageSize()) > cachedCaterers.size()
                ? cachedCaterers.size()
                : (start + pageable.getPageSize());

        caterers =
            new PageImpl<>(cachedCaterers.subList(start, end), pageable, cachedCaterers.size());
      }
      LOGGER.info("Cache Record [" + caterers.getContent() + "]");
    } else {
      LOGGER.info("Data is not cached for key CITY");
    }
    return caterers;
  }
}
