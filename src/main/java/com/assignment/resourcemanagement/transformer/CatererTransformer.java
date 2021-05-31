package com.assignment.resourcemanagement.transformer;

import com.assignment.resourcemanagement.boundaries.Caterer;
import com.assignment.resourcemanagement.mongo.docs.AddressDocument;
import com.assignment.resourcemanagement.mongo.docs.CapacityDocument;
import com.assignment.resourcemanagement.mongo.docs.CatererDocument;
import com.assignment.resourcemanagement.mongo.docs.ContactDocument;

public class CatererTransformer {

  public static CatererDocument toEntity(
      Caterer catererVO) {

    CatererDocument catererDocument = new CatererDocument();
    catererDocument.setName(catererVO.getName());

    CapacityDocument capacityDocument = new CapacityDocument();
    capacityDocument.setMinGuests(catererVO.getCapacity().getMinGuests());
    capacityDocument.setMaxGuests(catererVO.getCapacity().getMaxGuests());
    catererDocument.setCapacity(capacityDocument);

    AddressDocument addressDocument = new AddressDocument();
    addressDocument.setCity(catererVO.getAddress().getCity());
    addressDocument.setPostalCode(catererVO.getAddress().getPostalCode());
    addressDocument.setStreet(catererVO.getAddress().getStreet());
    catererDocument.setAddress(addressDocument);

    ContactDocument contactDocument = new ContactDocument();
    contactDocument.setEmail(catererVO.getContact().getEmail());
    contactDocument.setMobileNumber(catererVO.getContact().getMobileNumber());
    contactDocument.setPhoneNumber(catererVO.getContact().getPhoneNumber());
    catererDocument.setContact(contactDocument);

    return catererDocument;
  }

  public void toModel(CatererDocument catererDocumentDto) {

    /*  caterer.add(
        linkTo(methodOn(CatererController.class).getCatererByNameOrId(caterer.getId()))
            .withSelfRel());

    return caterer;*/
  }
}
