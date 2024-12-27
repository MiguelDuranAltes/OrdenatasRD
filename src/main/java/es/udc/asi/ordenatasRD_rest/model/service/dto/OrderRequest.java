package es.udc.asi.ordenatasRD_rest.model.service.dto;

import jakarta.validation.Valid;

import java.util.List;

public class OrderRequest {
  @Valid
  private OrderDTO order;

  @Valid
  private List<OrderProductDTO> orderProducts;

  public OrderRequest(OrderDTO order, List<OrderProductDTO> orderProducts) {
    this.order = order;
    this.orderProducts = orderProducts;
  }

  public OrderDTO getOrder() {
    return order;
  }

  public void setOrder(OrderDTO order) {
    this.order = order;
  }

  public List<OrderProductDTO> getOrderProducts() {
    return orderProducts;
  }

  public void setOrderProducts(List<OrderProductDTO> orderProducts) {
    this.orderProducts = orderProducts;
  }

}

