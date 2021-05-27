package com.assignment.resourcemanagement.handler;

import com.assignment.resourcemanagement.domain.Caterer;
import com.assignment.resourcemanagement.model.CatererListResponseModel;
import com.assignment.resourcemanagement.model.CatererModel;
import com.assignment.resourcemanagement.model.CatererRequestModel;
import com.assignment.resourcemanagement.model.CatererResponseModel;
import com.assignment.resourcemanagement.model.IModel;
import com.assignment.resourcemanagement.model.ResponseModel;
import com.assignment.resourcemanagement.service.CatererService;
import com.assignment.resourcemanagement.transformer.CatererTransformer;
import com.assignment.resourcemanagement.transformer.Transformer;
import com.assignment.resourcemanagement.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatererHandler {

  private final CatererService catererService;
  private final Transformer<Caterer, IModel> transformer;

  @Autowired
  public CatererHandler(CatererService catererService, final CatererTransformer transformer) {
    this.catererService = catererService;
    this.transformer = transformer;
  }

  public ResponseModel save(CatererRequestModel catererModel) {
    /*Converting object to Domain*/
    Caterer caterer = transformer.toDomain(catererModel);

    catererService.save(caterer);

    return new ResponseModel(
        CommonUtils.localizeResultMessage("message.model.caterer.save.success"));
  }

  public CatererResponseModel getCatererByNameOrId(String nameOrId) {

    Caterer caterer = this.catererService.getCatererByNameOrId(nameOrId);
    CatererModel model = (CatererModel) transformer.toModel(caterer);

    return new CatererResponseModel(
        CommonUtils.localizeResultMessage("message.model.caterer.get.success"), model);
  }

  public CatererListResponseModel getCaterersByCityName(String cityName, Pageable pageable) {

    Page<Caterer> caterer = this.catererService.getCaterersByCityName(cityName, pageable);

    List<CatererModel> models = (List<CatererModel>) transformer.toModels(caterer.getContent());


    Page<CatererModel> page = new PageImpl<>(models, caterer.getPageable(), caterer.getTotalElements());

    final CatererListResponseModel catererListResponseModel =
        new CatererListResponseModel(
            page, CommonUtils.localizeResultMessage("message.model.caterer.get.success"));
    return catererListResponseModel;
  }
}
