package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.Adress;
import jakarta.validation.constraints.NotEmpty;


public class AdressDTO {
  @NotEmpty
  private Long id;
  private String street;
  private int door;
  private int portal;
  private int postalCode;
  private String city;

  public AdressDTO() { super();}

  public AdressDTO(Adress adress) {
    this.id = adress.getId();
    this.street = adress.getStreet();
    this.door = adress.getDoor();
    this.portal = adress.getPortal();
    this.postalCode = adress.getPostalCode();
    this.city = adress.getCity();
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

  public int getDoor() {
    return door;
  }

  public void setDoor(int door) {
    this.door = door;
  }

  public int getPortal() {
    return portal;
  }

  public void setPortal(int portal) {
    this.portal = portal;
  }

  public int getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(int postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

}
