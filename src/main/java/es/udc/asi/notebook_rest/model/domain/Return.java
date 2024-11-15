package es.udc.asi.notebook_rest.model.domain;

public class Return extends ActionOverOrder {
  private String reason;

  public Return() {
    super();
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
