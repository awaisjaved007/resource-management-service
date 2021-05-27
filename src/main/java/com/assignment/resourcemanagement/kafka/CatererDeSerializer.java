package com.assignment.resourcemanagement.kafka;

import com.assignment.resourcemanagement.domain.Caterer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class CatererDeSerializer implements Deserializer<Caterer>
{
  @Override
  public Caterer deserialize(String s, byte[] bytes) {
    ObjectMapper mapper = new ObjectMapper();
    Caterer caterer = null;
    try {
      caterer = mapper.readValue(bytes, Caterer.class);
    } catch (Exception e) {

      e.printStackTrace();
    }
    return caterer;
  }
}
