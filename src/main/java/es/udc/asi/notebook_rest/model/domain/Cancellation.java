package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.Column;

public class Cancellation extends ActionOverOrder{
  @Column
  private String justification;

  public Cancellation() {
    super();
  }

  public String getJustification() {
    return justification;
  }

  public void setJustification(String justification) {
    this.justification = justification;
  }
}
