package es.udc.asi.ordenatasRD_rest.web;

import es.udc.asi.ordenatasRD_rest.model.exception.ModelException;
import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.service.ProductService;
import es.udc.asi.ordenatasRD_rest.model.service.dto.ImageDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.ProductDTO;
import es.udc.asi.ordenatasRD_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

  @GetMapping("/{id}/imagen")
  public void recuperarImagen(@PathVariable Long id, HttpServletResponse response) throws ModelException {
    ImageDTO imagen = productService.getProductImage(id);

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
    productService.saveProductImage(id, file);
  }

  @DeleteMapping("/{id}/imagen")
  public void borrarImagen(@PathVariable Long id) throws ModelException {
    productService.deleteProductImage(id);
  }

}
