package es.udc.asi.notebook_rest.model.repository;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import es.udc.asi.notebook_rest.model.domain.Category;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;

@Repository
public class CategoryDaoJpa extends GenericDaoJpa implements CategoryDao {

  @Override
  public void create(Category category) {
    entityManager.persist(category);
  }

  @Override
  public Category findById(Long id) {
    return entityManager.find(Category.class, id);
  }

  @Override
  public Collection<Category> findAll() {
    return entityManager.createQuery("from Category", Category.class).getResultList();
  }

  @Override
  public void delete(Category category) {
    entityManager.remove(category);

  }

  @Override
  public void update(Category category) {
    entityManager.merge(category);

  }

}
