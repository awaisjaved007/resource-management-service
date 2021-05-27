/**
 * ****************************************************************************** * * Copyright (c)
 * 2021. Seamless Distribution Systems AB - All Rights Reserved * * Unauthorized copying of this
 * file, via any medium is strictly prohibited. It is proprietary and confidential. * * Written by
 * mohitjain <mohit.jain@seamless.se>
 * *****************************************************************************
 */
package com.assignment.resourcemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.http.MediaType;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  static final String mediaTypeVersion1 = "application/json";

  @Primary
  @Bean
  public LinkDiscoverers discoverers() {
    List<LinkDiscoverer> plugins = new ArrayList<>();
    plugins.add(new CollectionJsonLinkDiscoverer());
    return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
  }

  @Bean
  public Docket api() {

    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("resource-management-system")
        .select()
        .apis(
            p -> {
              if (p.produces() != null) {
                for (MediaType mt : p.produces()) {
                  if (mt.toString().equals(mediaTypeVersion1)) {
                    return true;
                  }
                }
              }
              return false;
            })
        .build()
        .produces(Collections.singleton(mediaTypeVersion1))
        .apiInfo(
            new ApiInfoBuilder()
                .version("1")
                .title("Resource Management System API")
                .description("Documentation RMS API v1")
                .build());
  }
}
