package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

@Entity
public class OrderProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_product_generator")
  @SequenceGenerator(name = "order_product_generator", sequenceName = "orderprod_seq")
  Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  @Column
  private Integer quantity;

  public OrderProduct() {
    super();
  }

  public OrderProduct(Order order, Product product, Integer quantity) {
    super();
    this.order = order;
    this.product = product;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

}
