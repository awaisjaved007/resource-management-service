package com.assignment.resourcemanagement.interactors;

import com.assignment.resourcemanagement.boundaries.Caterer;
import com.assignment.resourcemanagement.boundaries.CatererInteractor;
import com.assignment.resourcemanagement.broker.MessageBroker;
import com.assignment.resourcemanagement.exception.InvalidDataException;
import com.assignment.resourcemanagement.exception.NotFoundException;
import com.assignment.resourcemanagement.mongo.docs.CatererDocument;
import com.assignment.resourcemanagement.mongo.repos.CatererRepository;
import com.assignment.resourcemanagement.transformer.CatererTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatererInteractorImpl implements CatererInteractor {

  private Logger LOGGER = LoggerFactory.getLogger(CatererInteractorImpl.class);
  private final CatererRepository catererRepository;
  private final MessageBroker messageBroker;

  @Value("${caterer.city.name.regex}")
  private String regex;

  @Autowired
  public CatererInteractorImpl(
      final CatererRepository catererRepository, final MessageBroker messageBroker) {
    this.catererRepository = catererRepository;
    this.messageBroker = messageBroker;
  }

  @Override
  @Caching(put = {@CachePut({"caterer"})})
  public void save(Caterer catererVO) {

    validateCaterer(catererVO);

    CatererDocument catererDocument = CatererTransformer.toEntity(catererVO);

    catererDocument = catererRepository.save(catererDocument);

    this.messageBroker.send("new-catererDocument", catererDocument);
  }

  @Override
  @Cacheable(value = "caterer")
  public CatererDocument getCatererByName(String name) {
    LOGGER.info("Fetching caterers by name [" + name + "]");
    return this.catererRepository
        .findByNameIgnoreCase(name)
        .orElseThrow(
            () ->
                new NotFoundException(
                    "message.request.getCaterer.id.is.invalid", new Object[] {name}));
  }

  @Override
  public Page<CatererDocument> getCaterersByCityName(String cityName, int page, int size) {
    LOGGER.info("Fetching caterers by cityName [" + cityName + "]");
    Pageable pageable = PageRequest.of(page - 1, size);

    Page<CatererDocument> caterers =
        this.catererRepository.findAllByAddress_CityIgnoreCase(cityName, pageable);
    return caterers;
  }

  private void validateCaterer(Caterer caterer) {
    if (caterer.getCapacity().getMinGuests() > caterer.getCapacity().getMaxGuests()) {
      throw new InvalidDataException("message.request.body.capacity.invalid.params");
    }

    if (!caterer.getAddress().getCity().matches(regex)) {
      throw new InvalidDataException("message.request.body.address.cityName.pattern.invalid");
    }

    Optional<CatererDocument> catererOptional =
        this.catererRepository.findByNameIgnoreCase(caterer.getName());

    if (catererOptional.isPresent()) {
      throw new InvalidDataException(
          "message.request.body.name.already.exists", new Object[] {caterer.getName()});
    }
  }
}
