package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.OrderChange;

public interface OrderChangeDao {

  void create(OrderChange orderChange);

  void delete(OrderChange orderChange);

  void update(OrderChange orderChange);

  OrderChange findById(Long id);
}
