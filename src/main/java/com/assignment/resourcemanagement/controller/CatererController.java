package com.assignment.resourcemanagement.controller;

import com.assignment.resourcemanagement.api.reqs.CatererRequest;
import com.assignment.resourcemanagement.boundaries.CatererInteractor;
import com.assignment.resourcemanagement.boundaries.PersistedCaterer;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Api(value = "REST APIs for Caterers")
@RestController
@RequestMapping(value = "/caterer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatererController {

  private final CatererInteractor catererInteractor;

  @Value("${caterer.get.city.name.api.page.size.default:10}")
  private int defaultPageSize;

  @Autowired
  public CatererController(CatererInteractor catererInteractor) {
    this.catererInteractor = catererInteractor;
  }

  @PostMapping
  public ResponseEntity<?> saveCaterer(@RequestBody @Valid CatererRequest caterer) {

    catererInteractor.save(caterer);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping(value = "{name}")
  public ResponseEntity<PersistedCaterer> getCatererById(@PathVariable String name) {

    PersistedCaterer persistedCaterer = catererInteractor.getCatererByName(name);

    return ResponseEntity.status(HttpStatus.OK).body(persistedCaterer);
  }

  @GetMapping(value = "all/{cityName}")
  public ResponseEntity<Page<? extends PersistedCaterer>> getCaterersListByCityName(
      @PathVariable String cityName,
      @RequestParam(value = "page") @Positive(message = "page.number.is.invalid") int page,
      @RequestParam(value = "size", required = false, defaultValue = "5")
          @Positive(message = "page.size.is.invalid")
          Integer size) {

    Page<? extends PersistedCaterer> responseModel =
        catererInteractor.getCaterersByCityName(cityName, page, size);

    return ResponseEntity.ok(responseModel);
  }
}
