package es.udc.asi.ordenatasRD_rest.model.service.dto;


import es.udc.asi.ordenatasRD_rest.model.domain.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PaymentMethodDTO {
  private Long id;
  @Size(min = 16, max = 16, message = "La tarjeta debe tener los dígitos correctos")
  private String creditCardNumber;
  @Size(min = 3, max = 3, message = "El CVV debe tener exactamente 3 dígitos")
  private String cvv;
  @NotEmpty
  private String name;
  @NotEmpty
  private String expirationDate;

  public PaymentMethodDTO() {

  }

  public PaymentMethodDTO(PaymentMethod PaymentMethod) {
    this.id = PaymentMethod.getId();
    this.creditCardNumber = PaymentMethod.getCreditCardNumber();
    this.cvv = PaymentMethod.getCvv();
    this.name = PaymentMethod.getName();
    this.expirationDate = PaymentMethod.getExpirationDate();
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditNumber(String creditNumber) {
    this.creditCardNumber = creditNumber;
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

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }
}
