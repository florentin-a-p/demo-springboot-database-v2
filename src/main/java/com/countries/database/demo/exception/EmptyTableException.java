package com.countries.database.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="To show an example of a custom message")
public class EmptyTableException extends Exception {
  public EmptyTableException(String message)
  {
    super(message);
  }
}
