package es.udc.asi.notebook_rest.model.service.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.udc.asi.notebook_rest.model.service.dto.AdressDTO;
import es.udc.asi.notebook_rest.model.service.dto.PaymentMethodDTO;
import es.udc.asi.notebook_rest.model.domain.Adress;
import es.udc.asi.notebook_rest.model.domain.PaymentMethod;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.domain.UserAuthority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserDTOPrivate {
  private Long id;

  @NotEmpty
  private String login;

  private UserAuthority authority;

  private String password;

  private Integer warnings;

  private List<AdressDTO> adresses = new ArrayList<>();

  private List<PaymentMethodDTO> paymentMethods = new ArrayList<>();

  public UserDTOPrivate() {
  }

  public UserDTOPrivate(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.authority = user.getAuthority();
    this.warnings = user.getWanings();
    user.getAdresses().forEach(adres -> this.adresses.add(new AdressDTO(adres)));

    user.getPaymentMethods().forEach(method -> this.paymentMethods.add(new PaymentMethodDTO(method)));
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

  public UserAuthority getAuthority() {
    return authority;
  }

  public void setAuthority(UserAuthority authority) {
    this.authority = authority;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getWarnings() {
    return warnings;
  }

  public void setWarnings(Integer warnings) {
    this.warnings = warnings;
  }

  public List<AdressDTO> getAdresses() {
    return adresses;
  }

  public void setAdresses(List<AdressDTO> adresses) {
    this.adresses = adresses;
  }

  public List<PaymentMethodDTO> getPaymentMethods() {
    return paymentMethods;
  }

  public void setPaymentMethods(List<PaymentMethodDTO> paymentMethods) {
    this.paymentMethods = paymentMethods;
  }
}
