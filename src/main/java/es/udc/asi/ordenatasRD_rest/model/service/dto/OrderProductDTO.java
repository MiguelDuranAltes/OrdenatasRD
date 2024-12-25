package es.udc.asi.ordenatasRD_rest.model.service.dto;

import jakarta.validation.constraints.NotNull;

public class OrderProductDTO {
  @NotNull
  private Long productId;
  @NotNull
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
