package es.udc.asi.ordenatasRD_rest.model.service;

import es.udc.asi.ordenatasRD_rest.model.domain.PaymentMethod;
import es.udc.asi.ordenatasRD_rest.model.domain.User;
import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.repository.PaymentMethodDao;
import es.udc.asi.ordenatasRD_rest.model.repository.UserDao;
import es.udc.asi.ordenatasRD_rest.model.service.dto.PaymentMethodDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.PaymentPublicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PaymentService {
  @Autowired
  private PaymentMethodDao paymentMethodDao;
  @Autowired
  private UserDao userDAO;
  @Autowired
  private UserService userService;

  public List<PaymentPublicDTO> findByUser(Long id){
    User user = userDAO.findById(id);
    Collection<PaymentMethod> paymentMethods = paymentMethodDao.findByUser(user);
    return paymentMethods.stream().map(paymentMethod -> new PaymentPublicDTO(paymentMethod)).collect(Collectors.toList());
  }

  public PaymentPublicDTO findById(Long id) throws NotFoundException{
    PaymentMethod paymentMethod = paymentMethodDao.findById(id);
    if (paymentMethod == null) {
      throw new NotFoundException(id.toString(), PaymentMethod.class);
    }
    return new PaymentPublicDTO(paymentMethod);
  }
  @PreAuthorize("hasAuthority('USER')")
  @Transactional(readOnly = false)
  public PaymentMethodDTO create(PaymentMethodDTO paymentMethod){

    User currentUser = userDAO.findById(userService.getCurrentUserWithAuthority().getId());
    PaymentMethod paymentMethodFinal = new PaymentMethod(paymentMethod.getCreditCardNumber(), paymentMethod.getCvv(), paymentMethod.getName(),
      paymentMethod.getExpirationDate(),currentUser);
    paymentMethodDao.create(paymentMethodFinal);
    currentUser.getPaymentMethods().add(paymentMethodFinal);

    return new PaymentMethodDTO(paymentMethodFinal);
  }

  @Transactional(readOnly = false)
  public void delete(Long id) throws NotFoundException {
    //Compruebo si el método existe
    PaymentMethod errasedMethod = paymentMethodDao.findById(id);
    if (errasedMethod == null) {
      throw new NotFoundException(id.toString(), PaymentMethod.class);
    }
    //Veo si la elimino, o simplemente la desvinculo en caso de que se haya usado en un pedido
    if (paymentMethodDao.isMethodUsedInOrders(errasedMethod)) {
      // El método se ha usado en un pedido, se desvincula
      errasedMethod.setOwner(null);
      paymentMethodDao.update(errasedMethod);
    } else {
      // La dirección no se ha usado, elimínala
      paymentMethodDao.delete(errasedMethod);
    }
  }
}
