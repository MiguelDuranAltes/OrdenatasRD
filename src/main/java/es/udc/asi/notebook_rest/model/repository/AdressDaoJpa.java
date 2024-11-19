package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Adress;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import jakarta.persistence.TypedQuery;

import java.util.Collection;

public class AdressDaoJpa extends GenericDaoJpa implements AdressDao{
  @Override
  public void create(Adress adress) {

  }

  @Override
  public void delete(Adress adress) { entityManager.persist(adress); }

  @Override
  public void update(Adress adress) { entityManager.merge(adress); }

  @Override
  public Adress findById(Long id) { return entityManager.find(Adress.class, id); }

  @Override
  public Collection<Adress> findByUser(String login) {

    String queryStr = "select a from Adress a";
    queryStr += " where a.user.login = :login";

    TypedQuery<Adress> query = entityManager.createQuery(queryStr, Adress.class);

    query.setParameter("login", login);

    return query.getResultList();
  }
}
