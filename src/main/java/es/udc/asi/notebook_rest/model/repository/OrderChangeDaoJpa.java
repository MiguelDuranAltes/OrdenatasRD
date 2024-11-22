package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.OrderChange;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;

public class OrderChangeDaoJpa extends GenericDaoJpa implements OrderChangeDao {

    public void create(OrderChange orderChange) {
        entityManager.persist(orderChange);
    }

    public void delete(OrderChange orderChange) {
        entityManager.remove(orderChange);
    }

    public void update(OrderChange orderChange) {
      entityManager.merge(orderChange);
    }

    public OrderChange findById(Long id) {
      return entityManager.find(OrderChange.class, id);
    }
}
