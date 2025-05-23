package es.udc.asi.ordenatasRD_rest;

import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import es.udc.asi.ordenatasRD_rest.config.DatabaseLoader;
import es.udc.asi.ordenatasRD_rest.model.exception.UserLoginExistsException;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class OrdenatasRestApplication {
  private final Logger logger = LoggerFactory.getLogger(OrdenatasRestApplication.class);

  @Autowired
  @Lazy
  private DatabaseLoader databaseLoader;

  public static void main(String[] args) {
    SpringApplication.run(OrdenatasRestApplication.class, args);
  }

  @PostConstruct
  public void init() throws InterruptedException {
    try {
      databaseLoader.loadData();
    } catch (UserLoginExistsException | OperationNotAllowed e) {
      logger.error(e.getMessage(), e);
    }
  }
}
