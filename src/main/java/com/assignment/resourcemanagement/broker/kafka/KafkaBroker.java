package com.assignment.resourcemanagement.broker.kafka;

import com.assignment.resourcemanagement.broker.MessageBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class KafkaBroker implements MessageBroker {

  private static final Logger LGR = LoggerFactory.getLogger(KafkaBroker.class);

  private final KafkaTemplate<String, String> resourceInfoKafkaTemplate;

  @Autowired
  public KafkaBroker(KafkaTemplate<String, String> resourceInfoKafkaTemplate) {
    this.resourceInfoKafkaTemplate = resourceInfoKafkaTemplate;
  }

  @Async
  @Override
  public void send(String topic, Object payload) {
    LGR.info("Sending Json Serializer : {}", payload);
    LGR.info("--------------------------------");
    try {
      resourceInfoKafkaTemplate.send(topic, payload+"");
    } catch (Exception e) {
      LGR.error("Error in broker occurred", e);
    }
  }
}
