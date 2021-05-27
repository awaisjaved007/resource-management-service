/**
 * ****************************************************************************** * * Copyright (c)
 * 2021. Seamless Distribution Systems AB - All Rights Reserved * * Unauthorized copying of this
 * file, via any medium is strictly prohibited. It is proprietary and confidential. * * Written by
 * mohitjain <mohit.jain@seamless.se>
 * *****************************************************************************
 */
package com.assignment.resourcemanagement.config;

import com.assignment.resourcemanagement.utils.CommonUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Configuration
public class LocaleConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    String headerLang = request.getHeader("Accept-Language");
    if (!StringUtils.hasText(headerLang)) {
      return Locale.getDefault();
    }
    int length = headerLang.split("-").length;
    Locale locale;
    switch (length) {
      case 3:
        locale =
            new Locale(
                headerLang.split("-")[0], headerLang.split("-")[1], headerLang.split("-")[2]);
      case 2:
        locale = new Locale(headerLang.split("-")[0], headerLang.split("-")[1]);
      case 1:
        locale = new Locale(headerLang);
      default:
        locale = Locale.getDefault();

        CommonUtils.setLocale(locale);
        return locale;
    }
  }
}
