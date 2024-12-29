package es.udc.asi.ordenatasRD_rest.model.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import es.udc.asi.ordenatasRD_rest.model.exception.ModelException;
import es.udc.asi.ordenatasRD_rest.model.repository.*;
import es.udc.asi.ordenatasRD_rest.model.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.ordenatasRD_rest.model.domain.User;
import es.udc.asi.ordenatasRD_rest.model.domain.UserAuthority;
import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.exception.UserLoginExistsException;
import es.udc.asi.ordenatasRD_rest.security.SecurityUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService {

  @Autowired
  private UserDao userDAO;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ImageService imageService;

  @Autowired
  private UserAdressPaymentService helperService;

  @PreAuthorize("hasAuthority('ADMIN')")
  public List<UserDTOPublic> findAll() {
    Stream<UserDTOPublic> users = userDAO.findAll().stream().map(user -> new UserDTOPublic(user));
    if (SecurityUtils.getCurrentUserIsAdmin()) {
      return users.collect(Collectors.toList());
    }
    return users.filter(user -> !user.isBlocked()).collect(Collectors.toList());
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  //Con Orders, prq me va a hacer falta para mostrar sus orders asociados
  public UserWithOrdersDTO findOneOrders(Long id) throws NotFoundException {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }
    return new UserWithOrdersDTO(user);
  }

  public UserDTOPublic findOne(Long id) throws NotFoundException {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }
    return new UserDTOPublic(user);
  }

  @Transactional(readOnly = false)
  public void registerUser(String login, String password) throws UserLoginExistsException {
    registerUser(login, password, false);
  }

  @Transactional(readOnly = false)
  public void registerUser(String login, String password, boolean isAdmin) throws UserLoginExistsException {
    if (userDAO.findByLogin(login) != null) {
      throw new UserLoginExistsException(login);
    }

    User user = new User();
    String encryptedPassword = passwordEncoder.encode(password);

    user.setLogin(login);
    user.setPassword(encryptedPassword);
    user.setAuthority(UserAuthority.USER);
    if (isAdmin) {
      user.setAuthority(UserAuthority.ADMIN);
    }
    user.setWarnings(0);

    userDAO.create(user);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public UserDTOPublic updateBlocked(Long id, boolean blocked) throws NotFoundException, OperationNotAllowed {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }

    UserDTOPrivate currentUser = getCurrentUserWithAuthority();
    if (currentUser.getId().equals(user.getId())) {
      throw new OperationNotAllowed("The user cannot block/unblock itself");
    }

    user.setBlocked(blocked);
    userDAO.update(user);
    return new UserDTOPublic(user);
  }

  public UserDTOPrivate getCurrentUserWithAuthority() {
    String currentUserLogin = SecurityUtils.getCurrentUserLogin();
    if (currentUserLogin != null) {
      return new UserDTOPrivate(userDAO.findByLogin(currentUserLogin));
    }
    return null;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException, OperationNotAllowed {
    User theUser = userDAO.findById(id);
    if (theUser == null) {
      throw new NotFoundException(id.toString(), User.class);
    }
    UserDTOPrivate currentUser = getCurrentUserWithAuthority();
    if (currentUser.getId().equals(theUser.getId())) {
      throw new OperationNotAllowed("The user cannot remove itself");
    }

    //BORRAR ADRESSES, PAYMENTMETHODS Y DESVINCULAR ORDERS
    helperService.eliminateUserAdresses(id);
    helperService.eliminateUsersMethods(id);
    helperService.desvincularOrders(id);

    userDAO.delete(theUser);
  }

  @Transactional(readOnly = false)
  public void saveUserImage(Long id, MultipartFile file) throws ModelException {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }

    if (file.isEmpty()) {
      throw new ModelException("No se ha enviado ninguna imagen");
    }

    String nombreFichero = imageService.saveImage(file, id);
    user.setImageName(nombreFichero);
    userDAO.update(user);
  }

  public ImageDTO getUserImage(Long id) throws ModelException {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }

    return imageService.getImage(id, user.getImageName());
  }

  public void deleteUserImage(Long id) throws ModelException {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }

    imageService.deleteImage(id, user.getImageName());
    user.setImageName(null);
    userDAO.update(user);
    userDAO.flush();
  }

}
