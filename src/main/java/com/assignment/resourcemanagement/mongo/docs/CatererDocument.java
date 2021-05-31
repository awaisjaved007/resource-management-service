package com.assignment.resourcemanagement.mongo.docs;

import com.assignment.resourcemanagement.boundaries.Address;
import com.assignment.resourcemanagement.boundaries.Capacity;
import com.assignment.resourcemanagement.boundaries.Contact;
import com.assignment.resourcemanagement.boundaries.PersistedCaterer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document
public class CatererDocument implements PersistedCaterer {
  @Id private String id;

  @Size(min = 1, max = 255, message = "message.request.body.name.length")
  private String name;

  private AddressDocument address;

  private CapacityDocument capacity;

  private ContactDocument contact;

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Capacity getCapacity() {
    return this.capacity;
  }

  @Override
  public Contact getContact() {
    return this.contact;
  }

  @Override
  public Address getAddress() {
    return this.address;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAddress(AddressDocument address) {
    this.address = address;
  }

  public void setCapacity(CapacityDocument capacity) {
    this.capacity = capacity;
  }

  public void setContact(ContactDocument contact) {
    this.contact = contact;
  }
}
