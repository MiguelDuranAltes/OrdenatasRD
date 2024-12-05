package es.udc.asi.ordenatasRD_rest.model.repository;

import es.udc.asi.ordenatasRD_rest.model.domain.PaymentMethod;
import es.udc.asi.ordenatasRD_rest.model.repository.util.GenericDaoJpa;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class PaymentMethodDaoJpa extends GenericDaoJpa implements PaymentMethodDao{
  @Override
  public void create(PaymentMethod paymentMethod) { entityManager.persist(paymentMethod); }

  @Override
  public void delete(PaymentMethod paymentMethod) { entityManager.remove(paymentMethod); }

  @Override
  public void update(PaymentMethod paymentMethod) { entityManager.merge(paymentMethod); }

  @Override
  public PaymentMethod findById(Long id) { return entityManager.find(PaymentMethod.class, id); }

  @Override
  public Collection<PaymentMethod> findByUser(String login) {

    String queryStr = "select pm from PaymentMethod pm";
    queryStr += " where pm.user.login = :login";

    TypedQuery<PaymentMethod> query = entityManager.createQuery(queryStr, PaymentMethod.class);

    query.setParameter("login", login);

    return query.getResultList();
  }
}
