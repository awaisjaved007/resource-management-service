package com.assignment.resourcemanagement.service.implementation;

import com.assignment.resourcemanagement.domain.Caterer;
import com.assignment.resourcemanagement.exception.InvalidDataException;
import com.assignment.resourcemanagement.exception.NotFoundException;
import com.assignment.resourcemanagement.kafka.NotificationStreamWriter;
import com.assignment.resourcemanagement.repository.CatererRepository;
import com.assignment.resourcemanagement.repository.RedisRepository;
import com.assignment.resourcemanagement.service.CatererService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CatererServiceImpl implements CatererService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CatererServiceImpl.class);

  private final CatererRepository catererRepository;

  private final NotificationStreamWriter notificationStreamWriter;

  private final RedisRepository redisRepository;

  @Value("${caterer.city.name.regex}")
  private String regex;

  @Value("${redis.is.enabled:false}")
  private Boolean isRedisEnabled;

  @Autowired
  public CatererServiceImpl(
      final CatererRepository catererRepository,
      final NotificationStreamWriter notificationStreamWriter,
      final RedisRepository redisRepository) {
    this.catererRepository = catererRepository;
    this.notificationStreamWriter = notificationStreamWriter;
    this.redisRepository = redisRepository;
  }

  @Override
  @Transactional
  public void save(Caterer caterer) {

    validateCaterer(caterer);

    caterer = this.catererRepository.save(caterer);

    this.notificationStreamWriter.sentNotification(caterer);
  }

  private void validateCaterer(Caterer caterer) {
    if (caterer.getCapacity().getMinGuests() > caterer.getCapacity().getMaxGuests()) {
      throw new InvalidDataException("message.request.body.capacity.invalid.params");
    }

    if (!caterer.getLocation().getCityName().matches(regex)) {
      throw new InvalidDataException("message.request.body.location.cityName.pattern.invalid");
    }

    Optional<Caterer> catererOptional =
        this.catererRepository.findByIdOrName(caterer.getName(), caterer.getName());

    if (catererOptional.isPresent()) {
      throw new InvalidDataException(
          "message.request.body.name.already.exists", new Object[] {caterer.getName()});
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Caterer getCatererByNameOrId(String nameOrId) {
    Optional<Caterer> caterer = this.catererRepository.findByIdOrName(nameOrId, nameOrId);
    if (caterer.isPresent()) {
      return caterer.get();
    } else {
      throw new NotFoundException(
          "message.request.getCaterer.nameOrId.is.invalid", new Object[] {nameOrId});
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Caterer> getCaterersByCityName(String cityName, Pageable pageable) {
    Page<Caterer> caterers;
    if (isRedisEnabled) {

      caterers = getCaterersFromRedis(cityName, pageable);

    } else {

      caterers = this.catererRepository.findAllByLocation_CityName(cityName, pageable);
      if (caterers.getContent().size() == 0) {

        throw new InvalidDataException("message.getCaterersByCityName.invalid.city.name");
      }
    }
    LOGGER.info("Database Record [" + caterers + "]");
    return caterers;
  }

  private Page<Caterer> getCaterersFromRedis(String cityName, Pageable pageable)
  {
    Page<Caterer> caterers;
    caterers = this.redisRepository.findByCityName("CITY", cityName, pageable);

    if (caterers.getContent().size() == 0) {

      List<Caterer> dbCaterers = this.catererRepository.findAllByLocation_CityName(cityName);

      if (dbCaterers.size() > 0) {

        this.redisRepository.save("CITY", cityName, dbCaterers);
        caterers = this.redisRepository.findByCityName("CITY", cityName, pageable);

      } else {

        throw new InvalidDataException("message.getCaterersByCityName.invalid.city.name");
      }
    }
    return caterers;
  }
}
