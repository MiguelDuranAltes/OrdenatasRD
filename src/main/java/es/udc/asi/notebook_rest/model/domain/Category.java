package es.udc.asi.notebook_rest.model.domain;

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
import jakarta.persistence.SequenceGenerator;

@Entity
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
  @SequenceGenerator(name = "category_generator", sequenceName = "category_seq")
  private Long id;

  @Column(unique = true)
  private String name;

  @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
  private Set<Note> notes = new HashSet<>();

  public Category() {
    super();
  }

  public Category(String name) {
    super();
    this.name = name;
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
    Category other = (Category) obj;
    return Objects.equals(id, other.id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Note> getNotes() {
    return notes;
  }

  public void setNotes(Set<Note> notes) {
    this.notes = notes;
  }

}
