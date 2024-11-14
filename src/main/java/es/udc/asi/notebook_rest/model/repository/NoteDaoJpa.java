package es.udc.asi.notebook_rest.model.repository;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import jakarta.persistence.TypedQuery;

@Repository
public class NoteDaoJpa extends GenericDaoJpa implements NoteDao {

  @Override
  public void create(Note note) {
    entityManager.persist(note);
  }

  @Override
  public void delete(Note note) {
    entityManager.remove(note);
  }

  @Override
  public Collection<Note> findAll(String category) {
    boolean hasCategory = category != null;

    String queryStr = "select distinct n from Note n";

    if (hasCategory) {
      queryStr += " join n.categories cat where cat.name = :category";
    }

    TypedQuery<Note> query = entityManager.createQuery(queryStr, Note.class);

    if (hasCategory) {
      query.setParameter("category", category);
    }

    return query.getResultList();
  }

  @Override
  public Collection<Note> findByUser(String login, String category) {
    boolean hasCategory = category != null;

    String queryStr = "select distinct n from Note n";

    if (hasCategory) {
      queryStr += " join n.categories cat";
    }

    queryStr += " where n.owner.login = :login";

    if (hasCategory) {
      queryStr += " and cat.name = :category";
    }

    TypedQuery<Note> query = entityManager.createQuery(queryStr, Note.class);

    query.setParameter("login", login);
    if (hasCategory) {
      query.setParameter("category", category);
    }

    return query.getResultList();
  }

  @Override
  public Note findById(Long id) {
    return entityManager.find(Note.class, id);
  }

  @Override
  public void deleteById(Long id) {
    Note note = findById(id);
    delete(note);
  }

  @Override
  public Collection<Note> findAllByCategory(Long id) {
    return entityManager.createQuery("select n from Note n join n.categories cat where cat.id = :catId", Note.class)
        .setParameter("catId", id).getResultList();
  }

  @Override
  public void update(Note note) {
    entityManager.merge(note);
  }

}
