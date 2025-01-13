package es.udc.asi.ordenatasRD_rest.model.service.dto;

import es.udc.asi.ordenatasRD_rest.model.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserWishListDto {
  private Long id;
  private List<ProductDTO> wishlist = new ArrayList<>();

  public UserWishListDto(){

  }

  public UserWishListDto(User user){
    this.id = user.getId();
    user.getWishlist().forEach(product -> this.wishlist.add(new ProductDTO(product)));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<ProductDTO> getWishlist() {
    return wishlist;
  }

  public void setWishlist(List<ProductDTO> wishlist) {
    this.wishlist = wishlist;
  }
}
