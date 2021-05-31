package com.assignment.resourcemanagement.config;

import com.assignment.resourcemanagement.utils.CommonUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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

  @Value("${kafka.host:broker-service}")
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

    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost + ":" + kafkaPort);
    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate kafkaTemplateString() {
    return new KafkaTemplate(producerFactoryString());
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
