package es.udc.asi.ordenatasRD_rest.web.exceptions;

public class CredentialsAreNotValidException extends ResourceException {

  public CredentialsAreNotValidException(String errorMsg) {
    super(errorMsg);
  }
}
