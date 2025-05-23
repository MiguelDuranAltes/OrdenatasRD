package es.udc.asi.ordenatasRD_rest.web;

import java.io.IOException;
import java.util.List;

import es.udc.asi.ordenatasRD_rest.model.exception.ModelException;
import es.udc.asi.ordenatasRD_rest.model.service.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserResource {

  @Autowired
  private UserService userService;

  @GetMapping
  public List<UserDTOPublic> findAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}/orders")
  public UserWithOrdersDTO findOneOrders(@PathVariable Long id) throws NotFoundException {
    return userService.findOneOrders(id);
  }

  @GetMapping("/{id}")
  public UserDTOPublic findOne(@PathVariable Long id) throws NotFoundException {
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

  @GetMapping("/{id}/imagen")
  public void recuperarImagen(@PathVariable Long id, HttpServletResponse response) throws ModelException {
    ImageDTO imagen = userService.getUserImage(id);

    try {
      response.setHeader("Content-disposition", "filename=" + imagen.getFilename());
      response.setContentType(imagen.getMimeType());
      IOUtils.copy(imagen.getInputStream(), response.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @PostMapping("/{id}/imagen")
  public void guardarImagen(@PathVariable Long id, @RequestParam MultipartFile file) throws ModelException {
    userService.saveUserImage(id, file);
  }

  @DeleteMapping("/{id}/imagen")
  public void borrarImagen(@PathVariable Long id) throws ModelException {
    userService.deleteUserImage(id);
  }

  @PostMapping("{id}/wishlist/{productId}")
  public void añadirProducto(@PathVariable Long id, @PathVariable Long productId) throws ModelException{
    userService.addProduct(id, productId);
  }

  @DeleteMapping("{id}/wishlist/{productId}")
  public void borrarProducto(@PathVariable Long id, @PathVariable Long productId) throws ModelException{
    userService.removeProduct(id, productId);
  }

  @GetMapping("{id}/wishlist")
  public UserWishListDto recuperarWishlist(@PathVariable Long id) throws ModelException{
    return userService.getWishlist(id);
  }
}
