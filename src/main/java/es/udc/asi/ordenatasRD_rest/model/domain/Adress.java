package es.udc.asi.ordenatasRD_rest.model.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "Adress")
public class Adress {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adress_generator")
  @SequenceGenerator(name = "adress_generator", sequenceName = "adress_seq")
  private Long id;

  @Column
  private String street;

  @Column
  private Integer door;

  @Column
  private Integer portal;

  @Column
  private String city;

  @Column
  private Integer postalCode;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  private User owner;


  public Adress() {
    super();
  }

  public Adress(String street, Integer door, Integer portal, String city, Integer postalCode, User owner) {
    this.street = street;
    this.door = door;
    this.portal = portal;
    this.city = city;
    this.postalCode = postalCode;
    this.owner = owner;
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
    Adress other = (Adress) obj;
    return Objects.equals(id, other.id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Integer getDoor() {
    return door;
  }

  public void setDoor(Integer door) {
    this.door = door;
  }

  public Integer getPortal() {
    return portal;
  }

  public void setPortal(Integer portal) {
    this.portal = portal;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(Integer postalCode) {
    this.postalCode = postalCode;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }



}
