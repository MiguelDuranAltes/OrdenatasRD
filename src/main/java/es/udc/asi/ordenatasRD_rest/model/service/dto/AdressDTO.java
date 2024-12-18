package es.udc.asi.ordenatasRD_rest.model.service.dto;

import es.udc.asi.ordenatasRD_rest.model.domain.Adress;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;


public class AdressDTO {
  private Long id;
  @NotEmpty
  private String street;
  private int door;
  private int portal;
  @Min(value = 1000, message = "El código postal debe ser mayor o igual a 1000")
  @Max(value = 99999, message = "El código postal debe ser menor o igual a 99999")  private int postalCode;
  @NotEmpty
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
