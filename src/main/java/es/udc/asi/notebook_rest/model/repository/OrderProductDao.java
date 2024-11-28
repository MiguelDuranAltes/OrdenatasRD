package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.OrderProduct;

import java.util.Collection;

public interface OrderProductDao {

  void create(OrderProduct orderProduct);

  void delete(OrderProduct orderProduct);

  void update(OrderProduct orderProduct);

  OrderProduct findById(Long id);

  Collection<OrderProduct> findByOrderId(Long orderId);

  Collection<OrderProduct> findByProductId(Long productId);

}
