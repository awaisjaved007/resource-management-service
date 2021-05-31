package com.assignment.resourcemanagement.controller;

import com.assignment.resourcemanagement.domain.Capacity;
import com.assignment.resourcemanagement.domain.Caterer;
import com.assignment.resourcemanagement.domain.Contact;
import com.assignment.resourcemanagement.domain.Location;
import com.assignment.resourcemanagement.handler.CatererHandler;
import com.assignment.resourcemanagement.model.CatererRequestModel;
import com.assignment.resourcemanagement.model.ResponseModel;
import com.assignment.resourcemanagement.repository.CatererRepository;
import com.assignment.resourcemanagement.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatererControllerTests {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private CatererHandler catererHandler;

  @Autowired private CatererRepository catererRepository;

  @BeforeEach
  public void setup() {
    this.catererRepository.deleteAll();
  }

  @Test
  public void addNewCaterer_Created() throws Exception {


    mockMvc
        .perform(
            post("/caterer")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(getNewCaterer())))
        .andExpect(status().isCreated());

    ResponseModel responseModel = catererHandler.getCatererByNameOrId("usman");

    assertEquals(
        responseModel.getResultDescription(),
        CommonUtils.localizeResultMessage("message.model.caterer.get.success"));
  }

  @Test
  public void addNewCatererWithInvalidCapacity_UnprocessableEntity() throws Exception {

    CatererRequestModel requestModel = getNewCaterer();
    requestModel.getCapacity().setMaxGuests(0);
    mockMvc
            .perform(
                    post("/caterer")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(requestModel)))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void addNewCatererWithInvalidEmail_UnprocessableEntity() throws Exception {

    CatererRequestModel requestModel = getNewCaterer();
    requestModel.getContact().setEmailAddress("abc");
    mockMvc
            .perform(
                    post("/caterer")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(requestModel)))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void getCaterer_NotFound() throws Exception {

    this.catererRepository.deleteAll();

    mockMvc
        .perform(get("/caterer/usman").contentType("application/json"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getCatererByName_Success() throws Exception {

    this.catererRepository.deleteAll();
    Caterer caterer = getCatererDomainObject();
    this.catererRepository.save(caterer);

    mockMvc.perform(get("/caterer/awais").contentType("application/json"))
            .andExpect(status().isOk());
  }

  private CatererRequestModel getNewCaterer() {
    CatererRequestModel caterer = new CatererRequestModel();
    caterer.setName("usman");
    CatererRequestModel.CapacityModel capacity = new CatererRequestModel.CapacityModel();
    capacity.setMinGuests(1);
    capacity.setMaxGuests(11);
    caterer.setCapacity(capacity);

    CatererRequestModel.LocationModel location = new CatererRequestModel.LocationModel();
    location.setCityName("lahore");
    location.setPostalCode("postalCode");
    location.setStreetNameNumber("123");
    caterer.setLocation(location);

    CatererRequestModel.ContactModel contact = new CatererRequestModel.ContactModel();
    contact.setEmailAddress("awais.javd@mail.com");
    contact.setMobileNumber("12345");
    contact.setPhoneNumber("12");
    caterer.setContact(contact);
    return caterer;
  }

  private Caterer getCatererDomainObject() {
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
