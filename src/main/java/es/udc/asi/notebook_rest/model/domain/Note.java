package es.udc.asi.notebook_rest.model.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Note {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_generator")
  @SequenceGenerator(name = "note_generator", sequenceName = "note_seq")
  private Long id;

  @Column
  private String title;

  @Column
  private String content;

  private LocalDateTime timestamp = LocalDateTime.now();

  private Boolean archived = false;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private User owner;

  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Category> categories = new HashSet<>();

  public Note() {
    super();
  }

  public Note(String title, String desc, User owner) {
    super();
    this.title = title;
    this.content = desc;
    this.owner = owner;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Note other = (Note) obj;
    return Objects.equals(id, other.id);
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

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

}
