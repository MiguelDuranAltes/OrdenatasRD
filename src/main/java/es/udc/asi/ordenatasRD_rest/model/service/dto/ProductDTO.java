package es.udc.asi.ordenatasRD_rest.model.service.dto;

import es.udc.asi.ordenatasRD_rest.model.domain.Product;
import jakarta.validation.constraints.*;

public class ProductDTO {
  private Long id;
  @NotEmpty
  private String name;
  @NotEmpty
  @Size(max = 200, message = "La descripción debe tener 200 caracteres como máximo")
  private String description;
  @NotNull(message = "El precio no puede ser nulo")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
  private Double price;
  @NotNull(message = "La disponibilidad no puede ser nula")
  @Min(value = 0, message = "La disponibilidad debe ser al menos 0")
  private Integer availability;
  @NotEmpty
  private String brand;

  private boolean hasImage;

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
    this.hasImage = product.getImageName() != null;
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

  public boolean isHasImage() {
    return hasImage;
  }

  public void setHasImage(boolean hasImage) {
    this.hasImage = hasImage;
  }
}
