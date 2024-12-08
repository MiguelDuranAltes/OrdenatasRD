package es.udc.asi.ordenatasRD_rest.web;

import java.io.IOException;
import java.util.List;

import es.udc.asi.ordenatasRD_rest.model.exception.ModelException;
import es.udc.asi.ordenatasRD_rest.model.service.dto.ImageDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.UserWithOrdersDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.service.UserService;
import es.udc.asi.ordenatasRD_rest.model.service.dto.UserDTOPublic;
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

  @GetMapping("/{id}/imagen")
  public void recuperarImagenDeNota(@PathVariable Long id, HttpServletResponse response) throws ModelException {
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
  public void guardarImagenDeNota(@PathVariable Long id, @RequestParam MultipartFile file) throws ModelException {
    userService.saveUserImage(id, file);
  }
}
