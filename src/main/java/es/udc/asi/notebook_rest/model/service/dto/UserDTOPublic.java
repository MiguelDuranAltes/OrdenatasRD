package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.User;

public class UserDTOPublic {
  private Long id;
  private String login;
  private boolean blocked = true;

  public UserDTOPublic() {
  }

  public UserDTOPublic(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.blocked = user.isBlocked();
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

  public boolean isBlocked() {
    return blocked;
  }

  public void setActive(boolean blocked) {
    this.blocked = blocked;
  }
}
