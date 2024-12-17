package es.udc.asi.ordenatasRD_rest.model.service;

import es.udc.asi.ordenatasRD_rest.model.domain.Adress;
import es.udc.asi.ordenatasRD_rest.model.domain.Order;
import es.udc.asi.ordenatasRD_rest.model.domain.PaymentMethod;
import es.udc.asi.ordenatasRD_rest.model.domain.User;
import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.repository.AdressDao;
import es.udc.asi.ordenatasRD_rest.model.repository.OrderDao;
import es.udc.asi.ordenatasRD_rest.model.repository.PaymentMethodDao;
import es.udc.asi.ordenatasRD_rest.model.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserAdressPaymentService {
  @Autowired
  private UserDao userDao;

  @Autowired
  private AdressDao adressDao;

  @Autowired
  private PaymentMethodDao paymentMethodDao;

  @Autowired
  private OrderDao orderDao;

  @Transactional
  public void eliminateUserAdresses(Long userId) throws NotFoundException {
    User user = userDao.findById(userId);
    if (user == null) {
      throw new NotFoundException(userId.toString(), User.class);
    }
    List<Adress> adresses = new ArrayList<>(user.getAdresses());

    if (adresses.isEmpty()){
      return;
    }

    for (Adress adress : adresses) {
      if (adressDao.isAdressUsedInOrders(adress)) {
        // La dirección se ha usado en un pedido, desvincúlala
        adress.setOwner(null);
        adressDao.update(adress);
      } else {
        // La dirección no se ha usado, elimínala
        adressDao.delete(adress);
      }
      user.getAdresses().remove(adress);
      userDao.update(user);
    }
  }

  @Transactional
  public void eliminateUsersMethods(Long userId) throws NotFoundException {
    User user = userDao.findById(userId);
    if (user == null) {
      throw new NotFoundException(userId.toString(), User.class);
    }
    List<PaymentMethod> methods = new ArrayList<>(user.getPaymentMethods());

    if (methods.isEmpty()){
      return;
    }

    for (PaymentMethod method : methods) {
      if (paymentMethodDao.isMethodUsedInOrders(method)) {
        // El método se ha usado en un pedido, desvincularlo
        method.setOwner(null);
        paymentMethodDao.update(method);
      } else {
        // El método no se ha usado, eliminarlo
        paymentMethodDao.delete(method);
      }
      user.getPaymentMethods().remove(method);
      userDao.update(user);
    }
  }

  @Transactional
  public void desvincularOrders(Long userId) throws NotFoundException{
    User user = userDao.findById(userId);
    if (user == null) {
      throw new NotFoundException(userId.toString(), User.class);
    }

    List<Order> orders = user.getOrders();
    if (orders.isEmpty()){
      return;
    }

    for (Order order : orders) {
      order.setUser(null);
      orderDao.update(order);
    }
  }
}
