package es.udc.asi.ordenatasRD_rest.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "theUser")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_seq")
  private Long id;

  @Column(unique = true)
  private String login;

  private String password;

  @Enumerated(EnumType.STRING)
  private UserAuthority authority;

  @Column
  private boolean blocked = false;

  @Column
  private Integer warnings;

  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private List<PaymentMethod> paymentMethods = new ArrayList<>();

  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private List<Adress> adresses = new ArrayList<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Order> orders = new ArrayList<>();

  @ManyToMany
  private List<Product> wishlist = new ArrayList<>();

  private String imageName;

  public User() { }

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
    User other = (User) obj;
    return Objects.equals(id, other.id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserAuthority getAuthority() {
    return authority;
  }

  public void setAuthority(UserAuthority authority) {
    this.authority = authority;
  }

  public boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public Integer getWarnings() {
    return warnings;
  }

  public void setWarnings(Integer warnings) {
    this.warnings = warnings;
  }

  public List<PaymentMethod> getPaymentMethods() {
    return paymentMethods;
  }

  public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
    this.paymentMethods = paymentMethods;
  }

  public List<Adress> getAdresses() {
    return adresses;
  }

  public void setAdresses(List<Adress> adresses) {
    this.adresses = adresses;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public List<Product> getWishlist() {
    return wishlist;
  }

  public void setWishlist(List<Product> wishlist) {
    this.wishlist = wishlist;
  }
}
