package es.udc.asi.notebook_rest.model.service.dto;


import es.udc.asi.notebook_rest.model.domain.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;

public class PaymentMethodDTO {
  @NotEmpty
  private Long id;
  private String creditNumber;
  private String cvv;
  private String name;

  public PaymentMethodDTO() {

  }

  public PaymentMethodDTO(PaymentMethod PaymentMethod) {
    this.id = PaymentMethod.getId();
    this.creditNumber = PaymentMethod.getCreditCardNumber();
    this.cvv = PaymentMethod.getCvv();
    this.name = PaymentMethod.getName();
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreditNumber() {
    return creditNumber;
  }

  public void setCreditNumber(String creditNumber) {
    this.creditNumber = creditNumber;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
