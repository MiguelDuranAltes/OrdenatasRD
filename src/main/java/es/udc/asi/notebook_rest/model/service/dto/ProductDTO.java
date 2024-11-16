package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.Product;

public class ProductDTO {
  private Long id;
  private String name;
  private String description;
  private Double price;
  private Integer availability;
  private String brand;

  public ProductDTO() {
    super();
  }

  public ProductDTO(Product product){
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.availability = product.getAvailability();
    this.brand = product.getBrand();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getAvailability() {
    return availability;
  }

  public void setAvailability(Integer availability) {
    this.availability = availability;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

}
