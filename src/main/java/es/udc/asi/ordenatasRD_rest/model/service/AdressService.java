package es.udc.asi.ordenatasRD_rest.model.service;

import es.udc.asi.ordenatasRD_rest.model.domain.Adress;
import es.udc.asi.ordenatasRD_rest.model.domain.User;
import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.repository.AdressDao;
import es.udc.asi.ordenatasRD_rest.model.repository.UserDao;
import es.udc.asi.ordenatasRD_rest.model.service.dto.AdressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)

public class AdressService {

  @Autowired
  private AdressDao adressDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private UserService userService;

  public List<AdressDTO> findByUser(String login){
    User user = userDao.findByLogin(login);
    Collection<Adress> adresses = adressDao.findByUser(user);
    return adresses.stream().map(adress -> new AdressDTO(adress)).collect(Collectors.toList());
  }

  public AdressDTO findById(Long id) throws NotFoundException {
    Adress adress = adressDao.findById(id);
    if (adress == null) {
      throw new NotFoundException(id.toString(), Adress.class);
    }
    return new AdressDTO(adress);
  }

  @Transactional(readOnly = false)
  public AdressDTO create(AdressDTO adress){
    User currentUser = userDao.findById(userService.getCurrentUserWithAuthority().getId());
    Adress adressFinal = new Adress(adress.getStreet(), adress.getDoor(), adress.getPortal(), adress.getCity(), adress.getPostalCode(), currentUser);
    adressDao.create(adressFinal);
    currentUser.getAdresses().add(adressFinal);
    return new AdressDTO(adressFinal);
  }

  @Transactional(readOnly = false)
  public void delete(Long id) throws NotFoundException {
    Adress errasedAdress = adressDao.findById(id);

    if (errasedAdress == null) {
      throw new NotFoundException(id.toString(), Adress.class);
    }
    adressDao.delete(errasedAdress);

  }

}
