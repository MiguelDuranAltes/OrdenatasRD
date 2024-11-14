package es.udc.asi.notebook_rest.model.repository;

import java.util.Collection;

import es.udc.asi.notebook_rest.model.domain.User;

public interface UserDao {

  Collection<User> findAll();

  User findById(Long id);

  User findByLogin(String login);

  void create(User user);

  void update(User user);

  void delete(User user);

}
