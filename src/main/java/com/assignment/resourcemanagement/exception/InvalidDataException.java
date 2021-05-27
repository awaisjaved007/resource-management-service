package com.assignment.resourcemanagement.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InvalidDataException extends RuntimeException {

  private Object[] args;

  /**
   * Instantiates a new missing data exception.
   *
   * @param message the message
   */
  public InvalidDataException(final String message) {
    super(message);
  }

  public InvalidDataException(final String message, Object[] args) {
    super(message);
    this.args = args;
  }
}
