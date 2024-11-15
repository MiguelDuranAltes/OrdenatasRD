package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ActionOverOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_generator")
  @SequenceGenerator(name = "action_generator", sequenceName = "action_seq")
  private Long id;

  @Column
  private Double refund;

  @Column
  private LocalDateTime date;

  @OneToOne(mappedBy = "action")
  private Order order;

  public ActionOverOrder() {
    super();
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
    ActionOverOrder other = (ActionOverOrder) obj;
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
}
