package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Adress;

import java.util.Collection;

public interface AdressDao {

  void create(Adress adress);

  void delete(Adress adress);

  void update(Adress adress);

  Adress findById(Long id);

  Collection<Adress> findByUser(String login);

}
