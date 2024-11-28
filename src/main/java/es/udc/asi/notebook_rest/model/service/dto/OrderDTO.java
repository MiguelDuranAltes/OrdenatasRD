package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.Order;
import es.udc.asi.notebook_rest.model.domain.Product;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

  private Long id;
  private String userLogin;
  private Double price;
  private LocalDateTime purchaseDate;
  private String status;
  private AdressDTO adress;
  private PaymentPublicDTO paymentMethod;

  public OrderDTO() {
    super();
  }

  public OrderDTO(Order order) {
    this.id = order.getId();
    this.userLogin = order.getUser().getLogin();
    this.price = order.getPrice();
    this.purchaseDate = order.getPurchaseDate();
    this.status = order.getStatus().toString();
    this.adress = new AdressDTO(order.getAdress());
    this.paymentMethod = new PaymentPublicDTO(order.getPaymentMethod());

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserLogin() {
    return userLogin;
  }

  public void setUserLogin(String userLogin) {
    this.userLogin = userLogin;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public LocalDateTime getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(LocalDateTime purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public AdressDTO getAdress() {
    return adress;
  }

  public void setAdress(AdressDTO adress) {
    this.adress = adress;
  }

  public PaymentPublicDTO getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(PaymentPublicDTO paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public static List<String> extractProductNames(List<Product> products) {
    return products.stream()
      .map(Product::getName)
      .collect(Collectors.toList());
  }
}
