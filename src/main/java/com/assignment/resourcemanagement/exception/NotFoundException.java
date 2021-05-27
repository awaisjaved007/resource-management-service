package com.assignment.resourcemanagement.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotFoundException extends RuntimeException {

  private Object[] args;

  /**
   * Instantiates a new missing data exception.
   *
   * @param message the message
   */
  public NotFoundException(final String message) {
    super(message);
  }

  public NotFoundException(final String message, Object[] args) {
    super(message);
    this.args = args;
  }
}
