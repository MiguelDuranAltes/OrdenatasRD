package es.udc.asi.notebook_rest.model.service.dto;

import java.util.ArrayList;
import java.util.List;



import es.udc.asi.notebook_rest.model.domain.User;
import jakarta.validation.constraints.NotEmpty;


public class UserWithOrdersDTO {
  private Long id;

  @NotEmpty
  private String login;
  private List<OrderDTO> orders = new ArrayList();

  public UserWithOrdersDTO() {
  }

  public UserWithOrdersDTO(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    user.getOrders().forEach(order -> this.orders.add(new OrderDTO(order)));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public List<OrderDTO> getOrders(){
    return orders;
  }

  public void setOrders(List<OrderDTO> orders){
    this.orders = orders;
  }
}
