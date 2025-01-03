package es.udc.asi.ordenatasRD_rest.web;

import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.service.ProductService;
import es.udc.asi.ordenatasRD_rest.model.service.dto.ProductDTO;
import es.udc.asi.ordenatasRD_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

  @Autowired
  private ProductService productService;

  @GetMapping
  public Collection<ProductDTO> findAll(){
    return productService.findAll();
  }

  @GetMapping("/{id}")
  public ProductDTO findOne(@PathVariable Long id) throws NotFoundException {
    return productService.findById(id);
  }


  @GetMapping("/brand/{brand}")
  public Collection<ProductDTO> findByBrand(@PathVariable String brand){
    return productService.findByBrand(brand);
  }

  @PostMapping
  public ProductDTO create(@RequestBody @Valid ProductDTO product, Errors errors ) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }
    return productService.create(product);
  }

  @PutMapping("/{id}")
  public ProductDTO update(@PathVariable Long id, @RequestBody @Valid ProductDTO product, Errors errors)
    throws RequestBodyNotValidException, NotFoundException, OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }
    if (id != product.getId()) {
      throw new RequestBodyNotValidException(errors);
    }
    return productService.update(product);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    productService.deleteById(id);
  }

}
