package es.udc.asi.ordenatasRD_rest.model.repository;

import es.udc.asi.ordenatasRD_rest.model.domain.OrderChange;
import es.udc.asi.ordenatasRD_rest.model.repository.util.GenericDaoJpa;
import org.springframework.stereotype.Repository;

@Repository
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
