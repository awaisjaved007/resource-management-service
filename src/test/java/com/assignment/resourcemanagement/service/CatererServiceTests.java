package com.assignment.resourcemanagement.service;

import com.assignment.resourcemanagement.domain.Capacity;
import com.assignment.resourcemanagement.domain.Caterer;
import com.assignment.resourcemanagement.domain.Contact;
import com.assignment.resourcemanagement.domain.Location;
import com.assignment.resourcemanagement.exception.InvalidDataException;
import com.assignment.resourcemanagement.repository.CatererRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CatererServiceTests {

  @Autowired private CatererService catererService;

  @MockBean private CatererRepository catererRepository;

  @BeforeAll
  public static void setup() {}

  @Test()
  public void saveCatererWithInvalidGuests_Failure() {

    Caterer caterer = getNewCaterer();

    Throwable exception =
        assertThrows(InvalidDataException.class, () -> this.catererService.save(caterer));

    assertEquals("message.request.body.capacity.invalid.params", exception.getMessage());
  }

  @Test()
  public void saveCatererWithInvalidCityName_Failure() {

    Caterer caterer = getNewCaterer();
    caterer.getCapacity().setMinGuests(1);
    Throwable exception =
        assertThrows(InvalidDataException.class, () -> this.catererService.save(caterer));

    assertEquals("message.request.body.location.cityName.pattern.invalid", exception.getMessage());
  }

  @Test()
  public void saveCatererWithAlreadyExistedUserName_Failure() {

    when(this.catererRepository.findByIdOrNameIgnoreCase(null, "awais")).thenReturn(Optional.empty());
    Caterer caterer = getNewCaterer();
    caterer.getCapacity().setMinGuests(1);
    Throwable exception =
        assertThrows(InvalidDataException.class, () -> this.catererService.save(caterer));

    assertEquals("message.request.body.location.cityName.pattern.invalid", exception.getMessage());
  }

  @Test()
  public void saveCaterer_Success() {

    Caterer caterer = getNewCaterer();
    caterer.getCapacity().setMinGuests(1);
    caterer.getLocation().setCityName("LAHORE");

    when(this.catererRepository.save(caterer)).thenReturn(caterer);

    Caterer savedCaterer = this.catererService.save(caterer);

    assertEquals(savedCaterer.getName(), caterer.getName());
  }

  private Caterer getNewCaterer() {
    Caterer caterer = new Caterer();
    caterer.setName("awais");
    Capacity capacity = new Capacity();
    capacity.setMinGuests(12);
    capacity.setMaxGuests(11);
    caterer.setCapacity(capacity);

    Location location = new Location();
    location.setCityName("123");
    location.setPostalCode("postalCode");
    location.setStreetNameNumber("123");
    caterer.setLocation(location);

    Contact contact = new Contact();
    contact.setEmailAddress("awais.javd@gmail.com");
    contact.setMobileNumber("12345");
    contact.setPhoneNumber("12");
    caterer.setContact(contact);
    return caterer;
  }
}
