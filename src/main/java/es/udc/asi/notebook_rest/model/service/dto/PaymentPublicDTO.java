package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;

public class PaymentPublicDTO {
  @NotEmpty
  private Long id;
  private String hiddenCardNumber;

  public PaymentPublicDTO(){

  }

  public PaymentPublicDTO(PaymentMethod paymentMethod){
    this.id=paymentMethod.getId();
    this.hiddenCardNumber = paymentMethod.getHiddenCardNumber(paymentMethod.getCreditCardNumber());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHiddenCardNumber() {
    return hiddenCardNumber;
  }

  public void setHiddenCardNumber(String hiddenCardNumber) {
    this.hiddenCardNumber = hiddenCardNumber;
  }
}
