package com.assignment.resourcemanagement.config;

import com.assignment.resourcemanagement.domain.Caterer;
import com.assignment.resourcemanagement.kafka.CatererDeSerializer;
import com.assignment.resourcemanagement.kafka.CatererSerializer;
import com.assignment.resourcemanagement.utils.CommonUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
public class ResourceManagementConfig extends AcceptHeaderLocaleResolver
    implements WebMvcConfigurer {

  @Value("${messages.source.files.path:resources}")
  private String messagePropertiesPath;

  @Autowired private MessageSource messageSource;

  @Value("${redis.host:redis}")
  private String redisHost;

  /*@Value("${redis.port:6379}")
  private Integer redisPort;
*/
  @Value("${kafka.host:kafka-service}")
  private String kafkaHost;

  @Value("${kafka.port:9092}")
  private int kafkaPort;

  @PostConstruct
  public void postConstruct() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    if (messagePropertiesPath.equals("resources")) {
      messageSource.setBasename("classpath:messages");
    } else {
      messageSource.setBasename("file:" + messagePropertiesPath + "/messages");
    }
    messageSource.setDefaultEncoding("UTF-8");
    this.messageSource = messageSource;
    CommonUtils.setMessageSource(messageSource);
    CommonUtils.setLocale(Locale.getDefault());
  }

  @Bean
  public LocalValidatorFactoryBean validator(MessageSource messageSource) {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource);
    return bean;
  }

  @Bean
  public ProducerFactory<String, String> producerFactoryString() {
    Map<String, Object> configProps = new HashMap<>();

    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost + ":" + kafkaPort);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, CatererSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CatererSerializer.class);

    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate kafkaTemplateString() {
    return new KafkaTemplate(producerFactoryString());
  }

  @Bean
  public ConsumerFactory<String, Caterer> consumerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost + ":" + kafkaPort);
    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, CatererDeSerializer.class);
    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CatererDeSerializer.class);

    return new DefaultKafkaConsumerFactory<>(configProps);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Caterer> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Caterer> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());

    return factory;
  }

  @Bean
  public RedisTemplate<String, Caterer> redisTemplate() {
    RedisTemplate<String, Caterer> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration =
        new RedisStandaloneConfiguration(redisHost);
    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }


  @Bean
  public ServletRegistrationBean dispatcherRegistration() {
    return new ServletRegistrationBean(dispatcherServlet());
  }

  @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
  public DispatcherServlet dispatcherServlet() {
    return new LoggableDispatcherServlet();
  }
}
