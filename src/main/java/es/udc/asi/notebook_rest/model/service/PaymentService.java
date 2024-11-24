package es.udc.asi.notebook_rest.model.service;

import es.udc.asi.notebook_rest.model.domain.PaymentMethod;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.PaymentMethodDao;
import es.udc.asi.notebook_rest.model.repository.UserDao;
import es.udc.asi.notebook_rest.model.service.dto.PaymentMethodDTO;
import es.udc.asi.notebook_rest.model.service.dto.PaymentPublicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PaymentService {
  @Autowired
  private PaymentMethodDao paymentMethod;
  @Autowired
  private UserDao userDAO;
  @Autowired
  private UserService userService;

  public List<PaymentPublicDTO> findByUser(String login){
    /*
    Sin pasar parámetro login y hacer:
    Stream<PaymentMethod> methods = paymentMethod.findByUser(SecurityUtils.getCurrentUserLogin()).stream();
    * */
    Stream<PaymentMethod> methods = paymentMethod.findByUser(login).stream();
    return methods.map(method -> new PaymentPublicDTO(method)).collect(Collectors.toList());
  }

  public PaymentPublicDTO findById(Long id) throws NotFoundException{
    PaymentMethod method = paymentMethod.findById(id);
    if (method == null) {
      throw new NotFoundException(id.toString(), PaymentMethod.class);
    }
    return new PaymentPublicDTO(method);
  }

  @Transactional(readOnly = false)
  public PaymentMethodDTO create(PaymentMethodDTO method){
    User currentUser = userDAO.findById(userService.getCurrentUserWithAuthority().getId());
    PaymentMethod paymentMethodFinal = new PaymentMethod(method.getCreditNumber(), method.getCvv(), method.getName(),
      method.getExpirationDate(),currentUser);
    paymentMethod.create(paymentMethodFinal);
    return new PaymentMethodDTO(paymentMethodFinal);
  }

  @Transactional(readOnly = false)
  public void delete(PaymentMethodDTO method) throws NotFoundException {
    PaymentMethod errasedMethod = paymentMethod.findById(method.getId());
    if (errasedMethod == null) {
      throw new NotFoundException(errasedMethod.getId().toString(), PaymentMethod.class);
    };
    paymentMethod.delete(errasedMethod);
  }

  //NOT SURE, se puede meter en el DAO un método para borrar lo M.Pago de un usuario en concreto
  @Transactional(readOnly = false)
  public void deleteUserMethods(User user) throws NotFoundException{
    Collection<PaymentMethod> deletedMethods = paymentMethod.findByUser(user.getLogin());
    deletedMethods.forEach(paymentMethod::delete);
  }

}
