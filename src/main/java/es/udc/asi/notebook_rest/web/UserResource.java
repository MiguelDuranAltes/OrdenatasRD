package es.udc.asi.notebook_rest.web;

import java.util.List;

import es.udc.asi.notebook_rest.model.service.dto.UserWithOrdersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.exception.OperationNotAllowed;
import es.udc.asi.notebook_rest.model.service.UserService;
import es.udc.asi.notebook_rest.model.service.dto.UserDTOPublic;

@RestController
@RequestMapping("/api/users")
public class UserResource {

  @Autowired
  private UserService userService;

  @GetMapping
  public List<UserDTOPublic> findAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public UserWithOrdersDTO findOne(@PathVariable Long id) throws NotFoundException {
    return userService.findOne(id);
  }

  @PutMapping("/{id}/block")
  public UserDTOPublic block(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    return userService.updateBlocked(id, true);
  }

  @DeleteMapping("/{id}/block")
  public UserDTOPublic unblock(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    return userService.updateBlocked(id, false);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    userService.deleteById(id);
  }
}
