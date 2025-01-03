package es.udc.asi.ordenatasRD_rest.web;

import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.service.AdressService;
import es.udc.asi.ordenatasRD_rest.model.service.dto.AdressDTO;
import es.udc.asi.ordenatasRD_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Vamos a coger solo las direcciones en relacion a un usuario, no se van a coger todas las registradas
//No va a haber un /users/adresses, sino un /users/id/adresses
@RequestMapping("/api/adresses")
public class AdressResource {

  @Autowired
  private AdressService adressService;

  @GetMapping("/{id}")
  public List<AdressDTO> findAll(@PathVariable Long id) throws NotFoundException {
    return adressService.findByUser(id);
  }

  @PostMapping
  //¿Validaciones en la adress?
  public AdressDTO create(@RequestBody @Valid AdressDTO adress, Errors errors) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }
    return adressService.create(adress);
  }

  //Replantearse si meter un delete en AdressService que reciba un id y no un DTO -> SOLVENTADO
  @DeleteMapping("{id}")
  public void delete(@PathVariable Long id) throws NotFoundException {
    adressService.delete(id);
  }
}

