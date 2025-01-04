package es.udc.asi.ordenatasRD_rest.config;

import es.udc.asi.ordenatasRD_rest.model.domain.*;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.ordenatasRD_rest.model.exception.UserLoginExistsException;
import es.udc.asi.ordenatasRD_rest.model.service.UserService;

import java.util.List;

@Configuration
public class DatabaseLoader {

  @Autowired
  private UserDao userDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private OrderDao orderDAO;

  @Autowired
  private ProductDao productDao;

  @Autowired
  private AdressDao adressDao;

  @Autowired
  private PaymentMethodDao paymentMethodDao;


  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void loadData() throws UserLoginExistsException, InterruptedException, OperationNotAllowed {
    userService.registerUser("redon", "redon@", true);
    userService.registerUser("duran", "duran@", true);
    userService.registerUser("lucas", "lucas@");
    userService.registerUser("pedroff", "pedroff@");
    User pedro = userDAO.findByLogin("pedroff");
    pedro.setBlocked(true);
    userDAO.update(pedro);
    userService.registerUser("miguel", "miguel@");

    User miguel = userDAO.findByLogin("miguel");
    Adress adress1 = new Adress("Calle Falsa",123, 1, "Springfield", 12345, miguel);
    Adress adress3 = new Adress("Calle ASecas",33, 11, "Poznan", 33446, miguel);
    miguel.getAdresses().add(adress1);
    miguel.getAdresses().add(adress3);
    adressDao.create(adress1);
    PaymentMethod paymentMethod1 = new PaymentMethod("1234567890123456", "123", "Miguel Duran", "12/26", miguel);
    PaymentMethod paymentMethod3 = new PaymentMethod("4321567890999999", "321", "Miguel Duran", "12/28", miguel);
    miguel.getPaymentMethods().add(paymentMethod1);
    miguel.getPaymentMethods().add(paymentMethod3);
    paymentMethodDao.create(paymentMethod1);

    //tengo que hacer el adressDao.create(adress1)?
    userDAO.update(miguel);

    User lucas = userDAO.findByLogin("lucas");
    lucas.setWarnings(2);
    Adress adress2 = new Adress("Calle Real", 456, 2, "Halloween", 12345, lucas);
    Adress adress4 = new Adress("Vegamot", 202, 4, "Trondheim", 13003, lucas);
    lucas.getAdresses().add(adress2);
    lucas.getAdresses().add(adress4);
    adressDao.create(adress2);
    adressDao.create(adress4);
    PaymentMethod paymentMethod2 = new PaymentMethod("9876543210987654", "321", "Lucas Redondo", "08/26", lucas);
    lucas.getPaymentMethods().add(paymentMethod2);
    paymentMethodDao.create(paymentMethod2);
    //lucas.setImageName("descarga.jpeg");
    userDAO.update(lucas);

    Product product1 = new Product("Tarjeta Gráfica", "Descripción detallada de la tarjeta", 300.0, "Asus", 10);
    Product product2 = new Product("Procesador", "Descripción detallada del procesador", 200.0, "Intel", 5);
    Product product3 = new Product("Placa Base", "Descripción detallada de la placa base", 150.0, "MSI", 3);
    Product product4 = new Product("Memoria RAM", "Descripción detallada de la memoria RAM", 100.0, "Corsair", 8);
    Product product5 = new Product("Disco Duro", "Descripción detallada del disco duro", 50.0, "Seagate", 0);
    Product product6 = new Product("Fuente de Alimentación", "Descripción detallada de la fuente de alimentación", 80.0, "Corsair", 4);
    Product product7 = new Product("Caja", "Descripción detallada de la caja", 70.0, "Corsair", 0);
    Product product8 = new Product("Ventilador", "Descripción detallada del ventilador", 30.0, "Corsair", 9);
    Product product9 = new Product("Monitor", "Descripción detallada del monitor", 200.0, "Asus", 2);
    Product product10 = new Product("Teclado", "Descripción detallada del teclado", 100.0, "Corsair", 1);

    productDao.create(product1);
    productDao.create(product2);
    productDao.create(product3);
    productDao.create(product4);
    productDao.create(product5);
    productDao.create(product6);
    productDao.create(product7);
    productDao.create(product8);
    productDao.create(product9);
    productDao.create(product10);


    //creo que en el orderDao o Service, tengo que reducir y aumentar el stock de los productos
    Order order1 = new Order(300.0, miguel, adress1, paymentMethod1);
    order1.setOrderProducts(List.of(new OrderProduct(order1, product1, 2), new OrderProduct(order1, product2, 3)));
    orderDAO.create(order1);
    Thread.sleep(1000);

    Order order2 = new Order(350.0 , lucas, adress2, paymentMethod2);
    order2.setOrderProducts(List.of(new OrderProduct(order2, product3, 1)));
    order2.setStatus(StatusOrder.DELIVERED);
    orderDAO.create(order2);
    Thread.sleep(1000);

    Order order3 = new Order(100.0, lucas, adress2, paymentMethod2);
    order3.setOrderProducts(List.of(new OrderProduct(order3, product4, 2), new OrderProduct(order3, product1, 5)));
    orderDAO.create(order3);
    Thread.sleep(1000);

    Order order4 = new Order(400.0, miguel, adress1, paymentMethod1);
    order4.setOrderProducts(List.of(new OrderProduct(order4, product10, 2), new OrderProduct(order4, product9, 1)));
  }

}
