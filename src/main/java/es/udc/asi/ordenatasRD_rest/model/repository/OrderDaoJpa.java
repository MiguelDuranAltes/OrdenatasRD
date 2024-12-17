package es.udc.asi.ordenatasRD_rest.model.repository;

import es.udc.asi.ordenatasRD_rest.model.domain.Order;
import es.udc.asi.ordenatasRD_rest.model.repository.util.GenericDaoJpa;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class OrderDaoJpa extends GenericDaoJpa implements OrderDao {

  public void create(Order order) {
    entityManager.persist(order);
  }

  public void delete(Order order) {
    entityManager.remove(order);
  }

  public void update(Order order) {
    entityManager.merge(order);
  }

  public Order findById(Long id) {
    return entityManager.find(Order.class, id);
  }

  public Collection<Order> findByUser(String login) {

    String queryStr = "select o from Order o";
    queryStr += " where o.user.login = :login";

    TypedQuery<Order> query = entityManager.createQuery(queryStr, Order.class);

    query.setParameter("login", login);

    return query.getResultList();
  }

  public Collection<Order> findAll() {

    String queryStr = "select o from Order o";

    TypedQuery<Order> query = entityManager.createQuery(queryStr, Order.class);

    return query.getResultList();
  }
}
