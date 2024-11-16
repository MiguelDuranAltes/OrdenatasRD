package es.udc.asi.notebook_rest.model.service.dto;

import java.time.LocalDateTime;

public class OrderChangeDTO {

  private Long id;
  private Long orderId;
  private Double refund;
  private LocalDateTime date;
  private String text;
  private String type;

  public OrderChangeDTO() {
    super();
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
