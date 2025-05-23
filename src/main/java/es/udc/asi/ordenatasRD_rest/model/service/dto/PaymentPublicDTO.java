package es.udc.asi.ordenatasRD_rest.model.service.dto;

import es.udc.asi.ordenatasRD_rest.model.domain.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;

public class PaymentPublicDTO {
  private Long id;
  @NotEmpty
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
