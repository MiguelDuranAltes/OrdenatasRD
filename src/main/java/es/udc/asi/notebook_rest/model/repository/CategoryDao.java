package es.udc.asi.notebook_rest.model.repository;

import java.util.Collection;

import es.udc.asi.notebook_rest.model.domain.Category;

public interface CategoryDao {

  void create(Category category);

  Category findById(Long id);

  Collection<Category> findAll();

  void delete(Category category);

  void update(Category category);

}
