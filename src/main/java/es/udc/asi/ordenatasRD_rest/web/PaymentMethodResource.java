package es.udc.asi.ordenatasRD_rest.web;

import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.service.PaymentService;
import es.udc.asi.ordenatasRD_rest.model.service.UserService;
import es.udc.asi.ordenatasRD_rest.model.service.dto.PaymentMethodDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.PaymentPublicDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.UserWithOrdersDTO;
import es.udc.asi.ordenatasRD_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/id/paymethods")
public class PaymentMethodResource {

  @Autowired
  private PaymentService paymentMethodService;

  @Autowired
  private UserService userService;

  @GetMapping // La excepción NotFoundException es por si da fallo el userService.findOne
  public List<PaymentPublicDTO> findAll(@PathVariable Long id) throws NotFoundException {
    UserWithOrdersDTO user = userService.findOne(id);
    return paymentMethodService.findByUser(user.getLogin());
  }

  @GetMapping("/{id}")
  public PaymentPublicDTO findOne(@PathVariable Long id) throws NotFoundException {
    return paymentMethodService.findById(id);
  }

  @PostMapping //No hace falta el usuario, ya que el PaymentService ya lo coge del contexto
  //@Valid para cuando metamos las validaciones en el DTO, como el mínimo de letras en el number...s
  public PaymentMethodDTO create(@RequestBody @Valid PaymentMethodDTO paymentMethod, Errors errors) throws RequestBodyNotValidException{
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }
    return paymentMethodService.create(paymentMethod);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable Long id) throws NotFoundException {
    paymentMethodService.delete(id);
  }

}
