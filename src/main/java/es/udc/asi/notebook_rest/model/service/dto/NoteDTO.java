package es.udc.asi.notebook_rest.model.service.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import es.udc.asi.notebook_rest.model.domain.Note;
import jakarta.validation.constraints.Size;

public class NoteDTO {
  private Long id;
  @Size(max = 300)
  private String title;
  private String content;
  private String owner;
  private Collection<CategoryDTO> categories = new ArrayList<>();
  private LocalDateTime timestamp;
  private Boolean archived;

  public NoteDTO() {
    super();
  }

  public NoteDTO(Note note) {
    this.id = note.getId();
    this.title = note.getTitle();
    this.content = note.getContent();
    this.owner = note.getOwner().getLogin();
    note.getCategories().forEach(c -> {
      this.categories.add(new CategoryDTO(c));
    });
    this.timestamp = note.getTimestamp();
    this.setArchived(note.getArchived());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Collection<CategoryDTO> getCategories() {
    return categories;
  }

  public void setCategories(Collection<CategoryDTO> categories) {
    this.categories = categories;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Boolean getArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

}
