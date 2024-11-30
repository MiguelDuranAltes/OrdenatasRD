package es.udc.asi.notebook_rest.model.service.dto;

import jakarta.validation.constraints.NotEmpty;

public class OrderProductDTO {

  private Long productId;
  @NotEmpty
  private Integer quantity;

    public OrderProductDTO() {
      super();
    }

    public Long getProductId() {
      return productId;
    }

    public void setProductId(Long productId) {
      this.productId = productId;
    }

    public Integer getQuantity() {
      return quantity;
    }

    public void setQuantity(Integer quantity) {
      this.quantity = quantity;
    }
}
