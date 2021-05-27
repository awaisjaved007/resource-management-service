package com.assignment.resourcemanagement.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Document
public class Caterer implements Serializable {
  @Id private String id;

  @Size(min = 1, max = 255, message = "message.request.body.name.length")
  @NotNull(message = "message.request.body.name.not.null")
  private String name;

  @NotNull(message = "message.request.body.location.not.null")
  private Location location;

  @NotNull(message = "message.request.body.capacity.not.null")
  private Capacity capacity;

  @NotNull(message = "message.request.body.contact.not.null")
  private Contact contact;
}
