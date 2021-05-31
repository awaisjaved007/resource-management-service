package com.assignment.resourcemanagement.controller;

import com.assignment.resourcemanagement.exception.InvalidDataException;
import com.assignment.resourcemanagement.handler.CatererHandler;
import com.assignment.resourcemanagement.model.CatererListResponseModel;
import com.assignment.resourcemanagement.model.CatererRequestModel;
import com.assignment.resourcemanagement.model.CatererResponseModel;
import com.assignment.resourcemanagement.model.ResponseModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "REST APIs for Caterers")
@Validated
@RestController
@RequestMapping("/caterer")
public class CatererController {

  private final String mediaTypeVersion = "application/json";

  private final CatererHandler catererHandler;

  @Value("${caterer.get.city.name.api.page.size.default:10}")
  private int defaultPageSize;

  @Autowired
  public CatererController(CatererHandler catererHandler) {
    this.catererHandler = catererHandler;
  }

  @PostMapping(produces = mediaTypeVersion)
  public ResponseEntity<ResponseModel> saveCaterer(
      @RequestBody @Valid CatererRequestModel catererModel) {

    ResponseModel responseModel = this.catererHandler.save(catererModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
  }

  @GetMapping(value = "{nameOrId}", produces = mediaTypeVersion)
  public ResponseEntity<CatererResponseModel> getCatererByNameOrId(@PathVariable String nameOrId) {

    CatererResponseModel responseModel = this.catererHandler.getCatererByNameOrId(nameOrId);

    return ResponseEntity.status(HttpStatus.OK).body(responseModel);
  }

  @GetMapping(value = "all/{cityName}", produces = mediaTypeVersion)
  public ResponseEntity<CatererListResponseModel> getCaterersListByCityName(
      @PathVariable String cityName,
      @RequestParam(value = "page") int page,
      @RequestParam(value = "size", required = false, defaultValue = "0") Integer size) {

    if (page < 1) {
      throw new InvalidDataException("page.number.is.invalid");
    }
    if (size != null && size < 0) {
      throw new InvalidDataException("page.size.is.invalid");
    }
    Pageable paging = PageRequest.of(page - 1, size == null || size == 0 ? defaultPageSize : size);
    ResponseModel responseModel = this.catererHandler.getCaterersByCityName(cityName, paging);

    return ResponseEntity.ok((CatererListResponseModel) responseModel);
  }
}
