package es.udc.asi.notebook_rest.web;

import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.service.AdressService;
import es.udc.asi.notebook_rest.model.service.UserService;
import es.udc.asi.notebook_rest.model.service.dto.AdressDTO;
import es.udc.asi.notebook_rest.model.service.dto.UserWithOrdersDTO;
import es.udc.asi.notebook_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Vamos a coger solo las direcciones en relacion a un usuario, no se van a coger todas las registradas
//No va a haber un /users/adresses, sino un /users/id/adresses
@RequestMapping("/api/users/id/adresses")
public class AdressResource {

  @Autowired
  private AdressService adressService;

  @Autowired
  private UserService userService;

  @GetMapping
  public List<AdressDTO> findAll(@PathVariable Long id) throws NotFoundException {
    UserWithOrdersDTO user = userService.findOne(id);
    return adressService.findByUser(user.getLogin());
  }

  @GetMapping("/{id}")
  public AdressDTO findOne(@PathVariable Long id) throws NotFoundException {
    return adressService.findById(id);
  }

  @PostMapping
  //¿Validaciones en la adress?
  public AdressDTO create(@RequestBody @Valid AdressDTO adress, Errors errors) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }
    return adressService.create(adress);
  }

  //Replantearse si meter un delete en AdressService que reciba un id y no un DTO
  @DeleteMapping("{id}")
  public void delete(@PathVariable Long id) throws NotFoundException {
    AdressDTO adress = new AdressDTO();
    //Vale que sea un DTO con solo el id, ya que el service es lo que utiliza, luego el DAO es el que recupera el existente
    //en la bd para posteriormente borrarlo
    adress.setId(id);
    adressService.delete(adress);
  }
}

