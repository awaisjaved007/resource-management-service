package com.assignment.resourcemanagement.kafka;

import com.assignment.resourcemanagement.domain.Caterer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class CatererSerializer implements Serializer<Caterer> {

  @Override
  public byte[] serialize(String arg0, Caterer arg1) {
    byte[] retVal = null;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      retVal = objectMapper.writeValueAsString(arg1).getBytes();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return retVal;
  }

  @Override
  public void close() {}
}
