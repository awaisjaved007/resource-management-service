package com.assignment.resourcemanagement.kafka;

import com.assignment.resourcemanagement.domain.Caterer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificationStreamWriter {

  private Logger logger = LoggerFactory.getLogger(NotificationStreamWriter.class);

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private KafkaTemplate<String, Caterer> resourceInfoKafkaTemplate;

  @Value("${resource.management.topic.name}")
  private String inventoryResourceTopicName;

  @Value("${resource.management.key:group_id}")
  private String inventoryResourceKey;

  public void sentNotification(Caterer caterer) {
    logger.info("Sending Json Serializer : {}", caterer);
    logger.info("--------------------------------");
    try {
      resourceInfoKafkaTemplate.send(inventoryResourceTopicName, caterer);
    } catch (Exception e) {
      logger.error("Error in kafka occurred: [" + e.getMessage() + "]");
    }
  }
}
