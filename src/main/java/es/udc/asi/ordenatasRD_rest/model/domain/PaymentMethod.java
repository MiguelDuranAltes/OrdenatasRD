package es.udc.asi.ordenatasRD_rest.model.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class PaymentMethod {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_generator")
  @SequenceGenerator(name = "payment_generator", sequenceName = "payment_seq")
  private Long id;

  @Column
  private String creditCardNumber;

  @Column
  private String cvv;

  @Column
  private String name;

  @Column
  private String expirationDate;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private User owner;

  public PaymentMethod() {
    super();
  }
  public PaymentMethod(String creditCardNumber,String cvv,String name,String expirationDate, User user){
    this.creditCardNumber = creditCardNumber;
    this.cvv = cvv;
    this.name = name;
    this.expirationDate = expirationDate;
    this.owner = user;

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
    PaymentMethod other = (PaymentMethod) obj;
    return Objects.equals(id, other.id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public String getHiddenCardNumber(String creditCardNumber) {
    return "**** " + creditCardNumber.substring(creditCardNumber.length() - 3);
  }
}
