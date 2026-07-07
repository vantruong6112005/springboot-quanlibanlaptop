package vantruong.iuh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vantruong.iuh.dto.response.CartItemResponse;
import vantruong.iuh.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productPrice", source = "product.price")
    @Mapping(target = "productThumbnail", source = "product.thumbnail")
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
