package com.pmobrien.vultus.liftoff.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ValidationException extends WebApplicationException {

  public ValidationException(String message) {
    super(message, Response.Status.CONFLICT.getStatusCode());   // always 409 for simplicity
  }
}
