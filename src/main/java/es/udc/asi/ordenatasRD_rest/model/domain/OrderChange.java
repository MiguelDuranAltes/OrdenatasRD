package es.udc.asi.ordenatasRD_rest.model.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class OrderChange {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_generator")
  @SequenceGenerator(name = "action_generator", sequenceName = "action_seq")
  private Long id;

  @Column
  private Double refund;

  @Column
  private LocalDateTime date = LocalDateTime.now();

  @OneToOne(mappedBy = "action")
  private Order order;

  @Column
  private String reason;

  @Column
  private String type;

  public OrderChange() {
    super();
  }

  public OrderChange(Double refund, Order order, String reason, String type) {
    super();
    this.refund = refund;
    this.order = order;
    this.reason = reason;

  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    OrderChange other = (OrderChange) obj;
    return Objects.equals(id, other.id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public String getReason(){
    return reason;
  }

  public void setReason(String reason){
    this.reason = reason;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
