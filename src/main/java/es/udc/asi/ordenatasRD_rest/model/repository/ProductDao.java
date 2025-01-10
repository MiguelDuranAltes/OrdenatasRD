package es.udc.asi.ordenatasRD_rest.model.repository;

import es.udc.asi.ordenatasRD_rest.model.domain.Product;

import java.util.Collection;
import java.util.List;

public interface ProductDao {
  void create(Product product);

  void delete(Product product);

  void update(Product product);

  Product findById(Long id);

  Collection<Product> findAll();

  Collection<Product> findByBrand(String brand);

  Collection<Product> findByNames(List<String> names);

  void flush();
}
