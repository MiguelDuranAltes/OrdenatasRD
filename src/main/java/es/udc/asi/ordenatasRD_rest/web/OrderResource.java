package es.udc.asi.ordenatasRD_rest.web;

import es.udc.asi.ordenatasRD_rest.model.domain.Order;
import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.service.OrderService;
import es.udc.asi.ordenatasRD_rest.model.service.dto.OrderDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.OrderRequest;
import es.udc.asi.ordenatasRD_rest.web.exceptions.IdAndBodyNotMatchingOnUpdateException;
import es.udc.asi.ordenatasRD_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
}
