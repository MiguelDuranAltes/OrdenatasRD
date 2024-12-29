package es.udc.asi.ordenatasRD_rest.model.service;

import es.udc.asi.ordenatasRD_rest.model.exception.ModelException;
import es.udc.asi.ordenatasRD_rest.model.service.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
  String saveImage(MultipartFile file, Long id) throws ModelException;

  ImageDTO getImage(Long id, String nombreImagen) throws ModelException;

  void deleteImage(Long id, String nombreImagen) throws ModelException;
}
