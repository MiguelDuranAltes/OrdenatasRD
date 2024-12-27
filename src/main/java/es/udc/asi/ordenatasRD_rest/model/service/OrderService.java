package es.udc.asi.ordenatasRD_rest.model.service;

import es.udc.asi.ordenatasRD_rest.model.domain.*;
import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.repository.*;
import es.udc.asi.ordenatasRD_rest.model.service.dto.*;
import es.udc.asi.ordenatasRD_rest.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OrderService {

  @Autowired
  private OrderDao orderDAO;

  @Autowired
  private UserDao userDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private PaymentMethodDao paymentMethodDao;

  @Autowired
  private ProductDao productDao;

  @Autowired
  private AdressDao adressDao;

  @Autowired
  private OrderChangeDao orderChangeDao;

  public Collection<OrderDTO> findAll() {
    Stream<Order> orders;

    if (SecurityUtils.getCurrentUserIsAdmin()) {
      orders = orderDAO.findAll().stream();
    } else {
      orders = orderDAO.findByUser(SecurityUtils.getCurrentUserLogin()).stream();
    }
    return orders.map(order -> new OrderDTO(order)).collect(Collectors.toList());
  }

  public List<OrderProductDTO> findProducts(Long id) throws NotFoundException {
    Order order = orderDAO.findById(id);
    List<OrderProduct> orderProducts = order.getOrderProducts();
    return orderProducts.stream().map(orderProduct -> new OrderProductDTO(orderProduct.getId(),orderProduct.getQuantity())).collect(Collectors.toList());
  }

  public OrderDTO findById(Long id) throws NotFoundException {
    Order order = orderDAO.findById(id);
    if(order == null) {
      throw new NotFoundException(id.toString(), Order.class);
    }
    return new OrderDTO(order);
  }

  @PreAuthorize("hasAuthority('USER')")
  @Transactional(readOnly = false)
  public OrderDTO create(OrderDTO order, List<OrderProductDTO> orderProductDTOS) throws OperationNotAllowed {
    User currentUser = userDAO.findById(userService.getCurrentUserWithAuthority().getId());
    PaymentMethod paymentMethod = paymentMethodDao.findById(order.getPaymentMethod().getId());
    Adress adress = adressDao.findById(order.getAdress().getId());

    Order bdOrder = new Order(order.getPrice(), currentUser, adress, paymentMethod);

    orderDAO.create(bdOrder);

    for (OrderProductDTO orderProductDTO : orderProductDTOS) {
      Product product = productDao.findById(orderProductDTO.getProductId());
      if(orderProductDTO.getQuantity() > product.getAvailability()) {
        orderDAO.delete(bdOrder);
        throw new OperationNotAllowed("Not enough stock for product " + product.getName());
      }
      OrderProduct orderProduct = new OrderProduct(bdOrder, product, orderProductDTO.getQuantity());
      bdOrder.getOrderProducts().add(orderProduct);
    }
    orderDAO.update(bdOrder);

    for(OrderProduct orderProduct : bdOrder.getOrderProducts()) {
      Product product = orderProduct.getProduct();
      product.setAvailability(product.getAvailability() - orderProduct.getQuantity());
      productDao.update(product);
    }
    return new OrderDTO(bdOrder);
  }

  @PreAuthorize("hasAuthority('USER')")
  @Transactional(readOnly = false)
  public OrderDTO returnOrder(OrderDTO order, OrderChangeDTO orderChangeDTO) throws NotFoundException, OperationNotAllowed {
    Order bdOrder = orderDAO.findById(order.getId());
    if(bdOrder == null) {
      throw new NotFoundException(order.getId().toString(), Order.class);
    }

    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();
    if (!bdOrder.getUser().getId().equals(currentUser.getId())) {
      throw new OperationNotAllowed("Current user is not the order owner");
    }

    bdOrder.setStatus(StatusOrder.RETURNED);

    OrderChange orderChange = new OrderChange(orderChangeDTO.getRefund(), bdOrder, orderChangeDTO.getText(), orderChangeDTO.getType());
    bdOrder.setAction(orderChange);

    orderChangeDao.create(orderChange);
    orderDAO.update(bdOrder);

    for(OrderProduct orderProduct : bdOrder.getOrderProducts()) {
      Product product = orderProduct.getProduct();
      product.setAvailability(product.getAvailability() + orderProduct.getQuantity());
      productDao.update(product);
    }

    return new OrderDTO(bdOrder);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public OrderDTO cancelOrder(OrderDTO order, OrderChangeDTO orderChangeDTO) throws NotFoundException {
    Order bdOrder = orderDAO.findById(order.getId());
    if(bdOrder == null) {
      throw new NotFoundException(order.getId().toString(), Order.class);
    }

    bdOrder.setStatus(StatusOrder.CANCELLED);

    OrderChange orderChange = new OrderChange(orderChangeDTO.getRefund(), bdOrder, orderChangeDTO.getText(), orderChangeDTO.getType());
    bdOrder.setAction(orderChange);

    orderChangeDao.create(orderChange);
    orderDAO.update(bdOrder);

    for(OrderProduct orderProduct : bdOrder.getOrderProducts()) {
      Product product = orderProduct.getProduct();
      product.setAvailability(product.getAvailability() + orderProduct.getQuantity());
      productDao.update(product);
    }

    return new OrderDTO(bdOrder);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public OrderDTO update(OrderDTO order) throws NotFoundException {
    Order bdOrder = orderDAO.findById(order.getId());
    if(bdOrder == null) {
      throw new NotFoundException(order.getId().toString(), Order.class);
    }

    // Convertir el status de String a StatusOrder (enumerado)
    if (order.getStatus() != null) {
      try {
        bdOrder.setStatus(StatusOrder.valueOf(order.getStatus().toUpperCase()));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid status: " + order.getStatus());
      }
    }

    orderDAO.update(bdOrder);
    return new OrderDTO(bdOrder);
  }

  //se va a poder borrar un pedido?
}
