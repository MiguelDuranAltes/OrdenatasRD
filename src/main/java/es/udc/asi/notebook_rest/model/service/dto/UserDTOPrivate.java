package es.udc.asi.notebook_rest.model.service.dto;


import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.domain.UserAuthority;
import jakarta.validation.constraints.NotEmpty;

public class UserDTOPrivate {
  private Long id;

  @NotEmpty
  private String login;

  private UserAuthority authority;

  private String password;

  private Integer warnings;

  public UserDTOPrivate() {
  }

  public UserDTOPrivate(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.authority = user.getAuthority();
    this.warnings = user.getWanings();
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
}
