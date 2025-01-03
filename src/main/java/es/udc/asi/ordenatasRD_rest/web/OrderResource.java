package es.udc.asi.ordenatasRD_rest.web;

import es.udc.asi.ordenatasRD_rest.model.domain.Order;
import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.exception.UserBlockedException;
import es.udc.asi.ordenatasRD_rest.model.service.OrderService;
import es.udc.asi.ordenatasRD_rest.model.service.dto.OrderChangeDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.OrderDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.OrderProductDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.OrderRequest;
import es.udc.asi.ordenatasRD_rest.web.exceptions.IdAndBodyNotMatchingOnUpdateException;
import es.udc.asi.ordenatasRD_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {

  @Autowired
  private OrderService orderService;

  @GetMapping
  public Collection<OrderDTO> findAll() {
    return orderService.findAll();
  }

  @GetMapping("/{id}")
  public OrderDTO findOne(@PathVariable Long id) throws NotFoundException {
    return orderService.findById(id);
  }

  @GetMapping("/{id}/products")
  public OrderRequest findProducts(@PathVariable Long id) throws NotFoundException {
    OrderDTO order = orderService.findById(id);
    List<OrderProductDTO> pedidos = orderService.findProducts(id);
    return new OrderRequest(order, pedidos);
  }

  @PostMapping
  public OrderDTO create(@RequestBody @Valid OrderRequest orderRequest, Errors errors) throws RequestBodyNotValidException, OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return orderService.create(orderRequest.getOrder(), orderRequest.getOrderProducts());
  }

  @PutMapping("/{id}")
  public OrderDTO update(@PathVariable Long id, @RequestBody @Valid OrderDTO order, Errors errors)
    throws RequestBodyNotValidException, IdAndBodyNotMatchingOnUpdateException, NotFoundException,
    OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    if (id != order.getId()) {
      throw new IdAndBodyNotMatchingOnUpdateException(Order.class);
    }

    return orderService.update(order);
  }

  @PostMapping("/{id}/cancel")
  public void cancelOrder (@PathVariable Long id,  @RequestBody @Valid OrderChangeDTO cancelacion) throws NotFoundException{
    OrderDTO order = orderService.findById(id);
    orderService.cancelOrder(order, cancelacion);
  }

  @PostMapping("/{id}/return")
  public void returnOrder (@PathVariable Long id,  @RequestBody @Valid OrderChangeDTO devolucion) throws NotFoundException, OperationNotAllowed, UserBlockedException {
    OrderDTO order = orderService.findById(id);
    orderService.returnOrder(order, devolucion);
  }
}
