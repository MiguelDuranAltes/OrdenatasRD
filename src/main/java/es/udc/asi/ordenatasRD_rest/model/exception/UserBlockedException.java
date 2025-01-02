package es.udc.asi.ordenatasRD_rest.model.exception;

public class UserBlockedException extends RuntimeException {
  public UserBlockedException(String message) {
    super(message);
  }
}
