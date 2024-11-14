package es.udc.asi.notebook_rest.model.service.dto;

import java.util.ArrayList;
import java.util.Collection;

import es.udc.asi.notebook_rest.model.domain.User;

public class UserWithNotesDTO {
  private Long id;
  private String login;
  private boolean active = true;
  private Collection<NoteDTO> notes = new ArrayList<NoteDTO>();

  public UserWithNotesDTO() {
    super();
  }

  public UserWithNotesDTO(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.active = user.isActive();
    user.getNotes().forEach(n -> this.notes.add(new NoteDTO(n)));
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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Collection<NoteDTO> getNotes() {
    return notes;
  }

  public void setNotes(Collection<NoteDTO> notes) {
    this.notes = notes;
  }

}
