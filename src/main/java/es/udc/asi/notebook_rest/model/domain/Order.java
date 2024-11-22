package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
  @SequenceGenerator(name = "order_generator", sequenceName = "order_seq")
  private Long id;

  @Column
  private Integer quantity;

  @Column
  private Double price;

  @Column
  private LocalDateTime purchaseDate;

  @Column
  private StatusOrder status;

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Product> products;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Adress adress;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private PaymentMethod paymentMethod;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  private OrderChange action;


  public Order() {
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
    Order other = (Order) obj;
    return Objects.equals(id, other.id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
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

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
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
