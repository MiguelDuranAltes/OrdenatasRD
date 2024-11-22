package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.Order;
import es.udc.asi.notebook_rest.model.domain.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

  private Long id;
  private List<String> products;
  private String userLogin;
  private Integer quantity;
  private Double price;
  private LocalDateTime purchaseDate;
  private String status;
  private AdressDTO adress;
  private String creditCardNumber;

  public OrderDTO() {
    super();
  }

  public OrderDTO(Order order) {
    this.id = order.getId();
    this.products = extractProductNames(order.getProducts());
    this.userLogin = order.getUser().getLogin();
    this.quantity = order.getQuantity();
    this.price = order.getPrice();
    this.purchaseDate = order.getPurchaseDate();
    this.status = order.getStatus().toString();
    this.adress = new AdressDTO(order.getAdress());
    this.creditCardNumber = order.getPaymentMethod().getCreditCardNumber();

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<String> getProducts() {
    return products;
  }

  public void setProducts(List<String> products) {
    this.products = products;
  }

  public String getUserLogin() {
    return userLogin;
  }

  public void setUserLogin(String userLogin) {
    this.userLogin = userLogin;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
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

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public static List<String> extractProductNames(List<Product> products) {
    return products.stream()
      .map(Product::getName)
      .collect(Collectors.toList());
  }
}
