package com.assignment.resourcemanagement.kafka;

import com.assignment.resourcemanagement.domain.Caterer;
import com.assignment.resourcemanagement.repository.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ConsumerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);
  private final RedisRepository redisRepository;

  @Value("${redis.is.enabled:false}")
  private Boolean isRedisEnabled;

  @Autowired
  public ConsumerService(final RedisRepository redisRepository) {
    this.redisRepository = redisRepository;
  }

  @KafkaListener(topics = "resource-management-topic", groupId = "group_id")
  public void consume(Caterer caterer) {
    LOGGER.info(String.format("$$$$ => Consumed message: %s", caterer));
    if (isRedisEnabled) {
      try {

        List<Caterer> caterers =
            this.redisRepository.findByCityName("CITY", caterer.getLocation().getCityName());

        caterers.add(caterer);

        this.redisRepository.save("CITY", caterer.getLocation().getCityName(), caterers);

      } catch (Exception e) {
        LOGGER.error("Kafka Consumer[" + e.getMessage() + "]");
      }
    }
  }
}
