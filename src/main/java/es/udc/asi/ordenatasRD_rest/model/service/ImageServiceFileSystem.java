package es.udc.asi.ordenatasRD_rest.model.service;

import es.udc.asi.ordenatasRD_rest.config.Properties;
import es.udc.asi.ordenatasRD_rest.model.exception.ModelException;
import es.udc.asi.ordenatasRD_rest.model.service.dto.ImageDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceFileSystem implements ImageService {

    @Autowired
    private Properties properties;

    private Path rootLocation;


    public String saveImage(MultipartFile file, Long id) throws ModelException {
      String filename = StringUtils.cleanPath(file.getOriginalFilename());

      try (InputStream is = file.getInputStream()) {
        Files.copy(is, getRootLocation().resolve(id + getExtension(filename)), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
        throw new ModelException("Se ha producido algún error procesando la imagen");
      }
      return filename;
    }

    private String getExtension(String filename) {
      return filename.substring(filename.lastIndexOf("."));
    }

    private Path getRootLocation() {
      if (rootLocation == null) {
        this.rootLocation = Paths.get(properties.getRutaImagenes());
      }
      return this.rootLocation;
    }

    public ImageDTO getImage(Long id, String nombreImagen) throws ModelException {
      try {
        InputStream is = new FileInputStream(properties.getRutaImagenes() + id + getExtension(nombreImagen));

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int len;
        while ((len = is.read(buffer)) > -1) {
          os.write(buffer, 0, len);
        }
        InputStream imageIs = new ByteArrayInputStream(os.toByteArray());
        os.flush();
        is.close();

        return new ImageDTO(imageIs, nombreImagen, getImageMediaType(nombreImagen));
      } catch (IOException e) {
        e.printStackTrace();
        throw new ModelException("se ha producido algún error al recuperar la imagen");
      }
    }

    private String getImageMediaType(String nombreImagen) {
      String extension = getExtension(nombreImagen);
      switch (extension.toLowerCase()) {
        case ".png":
          return MediaType.IMAGE_PNG_VALUE;
        case ".gif":
          return MediaType.IMAGE_GIF_VALUE;
        case ".jpg":
        case ".jpeg":
        default:
          return MediaType.IMAGE_JPEG_VALUE;
      }
    }
}
