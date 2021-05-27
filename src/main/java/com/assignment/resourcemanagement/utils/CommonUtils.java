package com.assignment.resourcemanagement.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class CommonUtils {
  private static final Logger LGR = LoggerFactory.getLogger(CommonUtils.class);
  private static MessageSource messageSource;
  private static Locale locale;

  public static void setMessageSource(MessageSource messageSource) {
    CommonUtils.messageSource = messageSource;
  }

  public static String localizeResultMessage(String message) {
    try {
      return messageSource.getMessage(message, null, locale);
    } catch (Exception e) {
      LGR.debug("No message found for message resource: " + message);
      return message;
    }
  }

  public static String localizeResultMessage(String message, Object[] args) {
    try {
      return messageSource.getMessage(message, args, locale);
    } catch (Exception e) {
      LGR.debug("No message found for message resource: " + message);
      return message;
    }
  }

  public static void setLocale(Locale locale) {
    CommonUtils.locale = locale;
  }
}
