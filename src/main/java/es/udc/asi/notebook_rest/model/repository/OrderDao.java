package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Order;

import java.util.Collection;

public interface OrderDao {
  void create(Order order);

  void delete(Order order);

  void update(Order order);

  Order findById(Long id);

  Collection<Order> findByUser(String login);

  Collection<Order> findAll();
}
