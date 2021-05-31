package com.assignment.resourcemanagement.service;

import com.assignment.resourcemanagement.boundaries.Address;
import com.assignment.resourcemanagement.boundaries.CatererInteractor;
import com.assignment.resourcemanagement.boundaries.PersistedCaterer;
import com.assignment.resourcemanagement.broker.MessageBroker;
import com.assignment.resourcemanagement.exception.InvalidDataException;
import com.assignment.resourcemanagement.mongo.docs.AddressDocument;
import com.assignment.resourcemanagement.mongo.docs.CapacityDocument;
import com.assignment.resourcemanagement.mongo.docs.CatererDocument;
import com.assignment.resourcemanagement.mongo.docs.ContactDocument;
import com.assignment.resourcemanagement.mongo.repos.CatererRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CatererServiceTests {

  @Autowired private CatererInteractor catererInteractor;

  @MockBean private CatererRepository catererRepository;


  @BeforeAll
  public static void setup() {}

  @Test()
  public void saveCatererWithInvalidGuests_Failure() {

    CatererDocument caterer = getNewCaterer();
    CapacityDocument capacityDocument = new CapacityDocument();
    capacityDocument.setMaxGuests(1);
    capacityDocument.setMinGuests(11);
    caterer.setCapacity(capacityDocument);
    Throwable exception =
        assertThrows(InvalidDataException.class, () -> this.catererInteractor.save(caterer));

    assertEquals("message.request.body.capacity.invalid.params", exception.getMessage());
  }

  @Test()
  public void saveCatererWithInvalidCityName_Failure() {

    CatererDocument caterer = getNewCaterer();
    AddressDocument addressDocument = new AddressDocument();
    addressDocument.setCity("123");
    caterer.setAddress(addressDocument);
    Throwable exception =
        assertThrows(InvalidDataException.class, () -> this.catererInteractor.save(caterer));

    assertEquals("message.request.body.address.cityName.pattern.invalid", exception.getMessage());
  }

  @Test()
  public void saveCatererWithAlreadyExistedUserName_Failure() {

    CatererDocument caterer = getNewCaterer();
    when(this.catererRepository.findByNameIgnoreCase("awais")).thenReturn(Optional.of(caterer));


    Throwable exception =
        assertThrows(InvalidDataException.class, () -> this.catererInteractor.save(caterer));

    assertEquals("message.request.body.name.already.exists", exception.getMessage());
  }

/*  @Test()
  public void saveCaterer_Success() {

    CatererDocument caterer = getNewCaterer();

    when(this.catererRepository.save(caterer)).thenReturn(caterer);

    CatererDocument persistedCaterer = this.catererInteractor.save(caterer);

    assertEquals(persistedCaterer.getName(), caterer.getName());
  }*/

  private CatererDocument getNewCaterer() {
    CatererDocument caterer = new CatererDocument();
    caterer.setName("awais");
    CapacityDocument capacityDocument = new CapacityDocument();
    capacityDocument.setMinGuests(10);
    capacityDocument.setMaxGuests(11);

    caterer.setCapacity(capacityDocument);

    AddressDocument location = new AddressDocument();
    location.setCity("LAHORE");
    location.setPostalCode("postalCode");
    location.setStreet("123");
    caterer.setAddress(location);

    ContactDocument contactDocument = new ContactDocument();
    contactDocument.setEmail("awais.javd@gmail.com");
    contactDocument.setMobileNumber("12345");
    contactDocument.setPhoneNumber("12");
    caterer.setContact(contactDocument);
    return caterer;
  }
}
