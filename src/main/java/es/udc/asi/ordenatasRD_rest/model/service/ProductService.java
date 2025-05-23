package es.udc.asi.ordenatasRD_rest.model.service;

import es.udc.asi.ordenatasRD_rest.model.domain.Product;
import es.udc.asi.ordenatasRD_rest.model.exception.ModelException;
import es.udc.asi.ordenatasRD_rest.model.exception.NotFoundException;
import es.udc.asi.ordenatasRD_rest.model.exception.OperationNotAllowed;
import es.udc.asi.ordenatasRD_rest.model.repository.ProductDao;
import es.udc.asi.ordenatasRD_rest.model.service.dto.ImageDTO;
import es.udc.asi.ordenatasRD_rest.model.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductService {

  @Autowired
  private ProductDao productdao;

  @Autowired
  private ImageService imageService;

  public Collection<ProductDTO> findAll(){
    Stream<Product> products = productdao.findAll().stream();
    return products.map(product -> new ProductDTO(product)).collect(Collectors.toList());
  }

  public Collection<ProductDTO> findByBrand(String marca) {
    Stream<Product> productos = productdao.findByBrand(marca).stream();
    return productos.map(product -> new ProductDTO(product)).collect(Collectors.toList());
  }

  public ProductDTO findById(Long id) throws NotFoundException{
    Product product = productdao.findById(id);
    if (product == null){
      throw new NotFoundException(id.toString(),Product.class);
    }
    return new ProductDTO(product);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public ProductDTO create(ProductDTO newProduct){
    Product product = new Product(newProduct.getName(), newProduct.getDescription(), newProduct.getPrice(),
      newProduct.getBrand(), newProduct.getAvailability());
    productdao.create(product);
    return new ProductDTO(product);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public ProductDTO update (ProductDTO product)throws NotFoundException, OperationNotAllowed{
    Product productBd = productdao.findById(product.getId());
    if (productBd == null) {
      throw new NotFoundException(product.getId().toString(), Product.class);
    }
    productBd.setName(product.getName());
    productBd.setDescription(product.getDescription());
    productBd.setPrice(product.getPrice());
    productBd.setBrand(product.getBrand());
    productBd.setAvailability(product.getAvailability());

    productdao.update(productBd);
    return new ProductDTO(productBd);

  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public void deleteById(Long id)throws NotFoundException{
    Product product = productdao.findById(id);
    if (product == null) {
      throw new NotFoundException(id.toString(), Product.class);
    }
    productdao.delete(product);
  }

  @Transactional(readOnly = false)
  public void saveProductImage(Long id, MultipartFile file) throws ModelException {
    Product product = productdao.findById(id);
    if (product == null) {
      throw new NotFoundException(id.toString(), Product.class);
    }

    if (file.isEmpty()) {
      throw new ModelException("No se ha enviado ninguna imagen");
    }

    String nombreFichero = imageService.saveImage(file, id, true);
    product.setImageName(nombreFichero);
    productdao.update(product);
  }

  public ImageDTO getProductImage(Long id) throws ModelException {
    Product product = productdao.findById(id);
    if (product == null) {
      throw new NotFoundException(id.toString(), Product.class);
    }

    return imageService.getImage(id, product.getImageName(), true);
  }

  public void deleteProductImage(Long id) throws ModelException {
    Product product = productdao.findById(id);
    if (product == null) {
      throw new NotFoundException(id.toString(), Product.class);
    }

    imageService.deleteImage(id, product.getImageName(), true);
    product.setImageName(null);
    productdao.update(product);
    productdao.flush();
  }
}
