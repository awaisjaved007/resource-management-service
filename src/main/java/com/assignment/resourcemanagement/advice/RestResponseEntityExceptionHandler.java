package com.assignment.resourcemanagement.advice;

import com.assignment.resourcemanagement.exception.InvalidDataException;
import com.assignment.resourcemanagement.exception.NotFoundException;
import com.assignment.resourcemanagement.utils.CommonUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {InvalidDataException.class})
  public ResponseEntity<Object> handleInvalidDataException(
      final InvalidDataException ex, final WebRequest request) {
    return ErrorMessage.create(
        CommonUtils.localizeResultMessage(ex.getMessage(), ex.getArgs()), request.getContextPath(),HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<Object> handleNotFoundDataException(
      final NotFoundException ex, final WebRequest request) {
    return ErrorMessage.create(
        CommonUtils.localizeResultMessage(ex.getMessage(), ex.getArgs()), request.getContextPath(),HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ResponseEntity<Object> handleConstraintViolationException(
      final ConstraintViolationException ex, final WebRequest request) {
    List<String> errors =
            ex.getConstraintViolations().stream().map(constraintViolation -> constraintViolation.getMessage()).collect(Collectors.toList());
    return ErrorMessage.create(
        CommonUtils.localizeResultMessage(CommonUtils.localizeResultMessage(errors.get(0))), request.getContextPath(),HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    // Get all errors

    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());

    return ErrorMessage.create(
        CommonUtils.localizeResultMessage(errors.get(0)), request.getContextPath(),HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {IllegalArgumentException.class})
  public ResponseEntity<Object> illegalArgument(
      final IllegalArgumentException ex, final WebRequest request) {

    return ErrorMessage.create(
        CommonUtils.localizeResultMessage(ex.getMessage()), request.getContextPath(),HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> badRequest(final Exception ex, final WebRequest request) {

    ex.printStackTrace();

    return ErrorMessage.create(
        CommonUtils.localizeResultMessage(ex.getMessage()), request.getContextPath(),HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @lombok.Data
  private static class ErrorMessage {
    private Timestamp timestamp;
    private HttpStatus status;
    private String error;
    private List<?> errors;
    private String message;
    private String path;

    private static ResponseEntity<Object> create(final String error, final String path, HttpStatus status) {
      final ErrorMessage message = new ErrorMessage();

      message.timestamp = new Timestamp(System.currentTimeMillis());
      message.status = status;
      message.error = status.toString();
      message.errors = Collections.EMPTY_LIST;
      message.message = error;
      message.path = path;

      return new ResponseEntity<>(message, new HttpHeaders(), status);
    }
  }
}
