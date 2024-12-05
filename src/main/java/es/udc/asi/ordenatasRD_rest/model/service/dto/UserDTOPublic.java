package es.udc.asi.ordenatasRD_rest.model.service.dto;

import es.udc.asi.ordenatasRD_rest.model.domain.User;
import jakarta.validation.constraints.NotEmpty;

public class UserDTOPublic {
  private Long id;
  @NotEmpty
  private String login;
  private boolean blocked = false;

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

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }
}
