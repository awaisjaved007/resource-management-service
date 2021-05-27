package com.assignment.resourcemanagement.config;

import com.assignment.resourcemanagement.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

  @Value("${cors.enable:false}")
  private boolean enableCors;

  @Value("${cors.allowedorigins:*}")
  private String allowedOrigins;

  private static final String[] HTTP_METHODS = {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"};

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    if (enableCors) {
      if (StringUtils.hasText(allowedOrigins)) {
        registry
            .addMapping("/**")
            .allowedOrigins(allowedOrigins.split(","))
            .allowedHeaders("*")
            .allowedMethods(HTTP_METHODS);
      } else {
        throw new InvalidDataException("Allowed origin cannot be empty if CORS policy is enabled.");
      }
    }
  }
}
