package es.udc.asi.ordenatasRD_rest.model.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class OrderChangeDTO {

  private Long id;
  @NotNull
  private Long orderId;
  @NotNull
  private Double refund;
  private LocalDateTime date;
  @NotEmpty
  @Size(max = 200)
  private String text;
  @NotEmpty
  private String type;

  public OrderChangeDTO() {
    super();
  }

  public OrderChangeDTO(OrderDTO order, LocalDateTime date, String text, String type){
    this.orderId = order.getId();
    this.refund = order.getPrice();
    this.date = date;
    this.text = text;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Double getRefund() {
    return refund;
  }

  public void setRefund(Double refund) {
    this.refund = refund;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
