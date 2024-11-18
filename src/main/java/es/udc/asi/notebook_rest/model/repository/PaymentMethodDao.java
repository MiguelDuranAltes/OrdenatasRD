package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.PaymentMethod;

import java.util.Collection;


public interface PaymentMethodDao {

  void create(PaymentMethod paymentMethod);

  void delete(PaymentMethod paymentMethod);

  void update(PaymentMethod paymentMethod);

  PaymentMethod findById(Long id);

  Collection<PaymentMethod> findByUser(String login);

}
