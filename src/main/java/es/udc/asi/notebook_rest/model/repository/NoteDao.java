package es.udc.asi.notebook_rest.model.repository;

import java.util.Collection;

import es.udc.asi.notebook_rest.model.domain.Note;

public interface NoteDao {

  void create(Note note);

  void delete(Note note);

  Collection<Note> findAll(String category);

  Collection<Note> findByUser(String login, String category);

  Note findById(Long id);

  void deleteById(Long id);

  Collection<Note> findAllByCategory(Long id);

  void update(Note note);

}
