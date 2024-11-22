package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.Product;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

@Repository
public class ProductDaoJpa extends GenericDaoJpa implements ProductDao{

    @Override
    public void create(Product product) {
      entityManager.persist(product);
    }

    @Override
    public void delete(Product product) {
      entityManager.remove(product);
    }

    @Override
    public void update(Product product) {
      entityManager.merge(product);
    }

    @Override
    public Collection<Product> findAll() { return entityManager.createQuery("Select p from Product p ", Product.class).getResultList();
    }

    @Override
    public Product findById(Long id) { return entityManager.find(Product.class, id); }

    @Override
    public Collection<Product> findByBrand(String brand){
      //Comprobar si la marca existe, aqui o en el front?
      String queryStr = "select p from Product p";

      queryStr += " where p.brand = :brand";

      TypedQuery<Product> query = entityManager.createQuery(queryStr, Product.class);

      query.setParameter("brand", brand);
      return query.getResultList();
    }
}
