package es.udc.asi.ordenatasRD_rest.model.repository;

import es.udc.asi.ordenatasRD_rest.model.domain.PaymentMethod;
import es.udc.asi.ordenatasRD_rest.model.domain.User;

import java.util.Collection;


public interface PaymentMethodDao {

  void create(PaymentMethod paymentMethod);

  void delete(PaymentMethod paymentMethod);

  void update(PaymentMethod paymentMethod);

  PaymentMethod findById(Long id);

  Collection<PaymentMethod> findByUser(User user);

}
