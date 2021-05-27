package com.assignment.resourcemanagement.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LoggableDispatcherServlet extends DispatcherServlet {

  private Logger logger = LoggerFactory.getLogger(LoggableDispatcherServlet.class);

  @Override
  protected void doDispatch(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    if (!(request instanceof ContentCachingRequestWrapper)) {
      request = new ContentCachingRequestWrapper(request);
    }
    if (!(response instanceof ContentCachingResponseWrapper)) {
      response = new ContentCachingResponseWrapper(response);
    }
    HandlerExecutionChain handler = getHandler(request);

    try {
      super.doDispatch(request, response);
    } finally {
      log(request, response, handler);
      updateResponse(response);
    }
  }

  private void log(
      HttpServletRequest requestToCache,
      HttpServletResponse responseToCache,
      HandlerExecutionChain handler) {
    logger.info(
        "\n\nREQUEST: {"
            + "METHOD:"
            + requestToCache.getMethod()
            + "HANDLER"
            + handler.toString()
            + "PAYLOAD:"
            + getRequestPayload(requestToCache)
            + "\n}");

    logger.info(
        "\n\nRESPONSE : {STATUS:"
            + responseToCache.getStatus()
            + "METHOD:"
            + requestToCache.getMethod()
            + "HANDLER:"
            + handler.toString()
            + "PAYLOAD:"
            + getResponsePayload(responseToCache)
            + "\n}");
  }

  private String getResponsePayload(HttpServletResponse response) {
    ContentCachingResponseWrapper wrapper =
        WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);

    return wrapper != null
        ? getPayLoadAsString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding())
        : "[unknown]";
  }

  private String getRequestPayload(HttpServletRequest request) {
    ContentCachingRequestWrapper wrapper =
        WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
    return getPayLoadAsString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
  }

  private String getPayLoadAsString(byte[] contentAsByteArray, String characterEncoding) {
    String payLoad = "[unknown]";
    byte[] buf = contentAsByteArray;
    if (buf.length > 0) {
      int length = Math.min(buf.length, 5120);
      try {
        payLoad = new String(buf, 0, length, characterEncoding);
      } catch (UnsupportedEncodingException ex) {
        payLoad = "{message:payload length is 0}";
      }
    }
    return payLoad;
  }

    private void updateResponse(HttpServletResponse response) throws IOException {
    ContentCachingResponseWrapper responseWrapper =
        WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    responseWrapper.copyBodyToResponse();
  }
}
