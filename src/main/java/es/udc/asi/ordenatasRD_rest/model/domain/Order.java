package es.udc.asi.ordenatasRD_rest.model.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
  @SequenceGenerator(name = "order_generator", sequenceName = "order_seq")
  private Long id;

  @Column
  private Double price;

  @Column
  private LocalDateTime purchaseDate = LocalDateTime.now();

  @Column
  @Enumerated(EnumType.STRING)
  private StatusOrder status = StatusOrder.NOT_SHIPPED;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderProduct> orderProducts = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Adress adress;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private PaymentMethod paymentMethod;

  @OneToOne(fetch = FetchType.LAZY, optional = true)
  private OrderChange action = null;


  public Order() {
    super();
  }

  public Order(Double price, User user,
               Adress adress, PaymentMethod paymentMethod) {
    super();
    this.price = price;
    this.user = user;
    this.adress = adress;
    this.paymentMethod = paymentMethod;
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
    Order other = (Order) obj;
    return Objects.equals(id, other.id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public LocalDateTime getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(LocalDateTime purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public StatusOrder getStatus() {
    return status;
  }

  public void setStatus(StatusOrder status) {
    this.status = status;
  }

  public List<OrderProduct> getOrderProducts() {
    return orderProducts;
  }

  public void setOrderProducts(List<OrderProduct> orderProducts) {
    this.orderProducts = orderProducts;
  }


  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Adress getAdress() {
    return adress;
  }

  public void setAdress(Adress adress) {
    this.adress = adress;
  }

  public PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public OrderChange getAction() {
    return action;
  }

  public void setAction(OrderChange action) {
    this.action = action;
  }

}
