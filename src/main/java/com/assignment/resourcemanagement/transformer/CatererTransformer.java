package com.assignment.resourcemanagement.transformer;

import com.assignment.resourcemanagement.controller.CatererController;
import com.assignment.resourcemanagement.domain.Capacity;
import com.assignment.resourcemanagement.domain.Caterer;
import com.assignment.resourcemanagement.domain.Contact;
import com.assignment.resourcemanagement.domain.Location;
import com.assignment.resourcemanagement.model.CatererModel;
import com.assignment.resourcemanagement.model.CatererRequestModel;
import com.assignment.resourcemanagement.model.IModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CatererTransformer implements Transformer<Caterer, IModel> {

  @Override
  public Caterer toDomain(IModel requestModel) {

    CatererRequestModel model = (CatererRequestModel) requestModel;
    Caterer caterer = new Caterer();
    caterer.setName(model.getName());

    Capacity capacity = new Capacity();
    capacity.setMinGuests(model.getCapacity().getMinGuests());
    capacity.setMaxGuests(model.getCapacity().getMaxGuests());
    caterer.setCapacity(capacity);

    Location location = new Location();
    location.setCityName(model.getLocation().getCityName());
    location.setPostalCode(model.getLocation().getPostalCode());
    location.setStreetNameNumber(model.getLocation().getStreetNameNumber());
    caterer.setLocation(location);

    Contact contact = new Contact();
    contact.setEmailAddress(model.getContact().getEmailAddress());
    contact.setMobileNumber(model.getContact().getMobileNumber());
    contact.setPhoneNumber(model.getContact().getPhoneNumber());
    caterer.setContact(contact);

    return caterer;
  }

  @Override
  public IModel toModel(Caterer catererDto) {

    CatererModel caterer = new CatererModel();

    caterer.setName(catererDto.getName());
    caterer.setId(catererDto.getId());

    CatererModel.CapacityModel capacity = new CatererModel.CapacityModel();
    capacity.setMinGuests(catererDto.getCapacity().getMinGuests());
    capacity.setMaxGuests(catererDto.getCapacity().getMaxGuests());
    caterer.setCapacity(capacity);

    CatererModel.LocationModel location = new CatererModel.LocationModel();
    location.setCityName(catererDto.getLocation().getCityName());
    location.setPostalCode(catererDto.getLocation().getPostalCode());
    location.setStreetNameNumber(catererDto.getLocation().getStreetNameNumber());
    caterer.setLocation(location);

    CatererModel.ContactModel contact = new CatererModel.ContactModel();
    contact.setEmailAddress(catererDto.getContact().getEmailAddress());
    contact.setMobileNumber(catererDto.getContact().getMobileNumber());
    contact.setPhoneNumber(catererDto.getContact().getPhoneNumber());
    caterer.setContact(contact);

    caterer.add(
        linkTo(methodOn(CatererController.class).getCatererByNameOrId(caterer.getId()))
            .withSelfRel());

    return caterer;
  }
}
