package com.assignment.resourcemanagement.broker;

import com.assignment.resourcemanagement.mongo.docs.CatererDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class CatererSerializer implements Serializer<CatererDocument> {

  @Override
  public byte[] serialize(String arg0, CatererDocument arg1) {
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
