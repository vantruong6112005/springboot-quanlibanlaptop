package vantruong.iuh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vantruong.iuh.dto.response.CartResponse;
import vantruong.iuh.entity.Cart;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {
    @Mapping(target = "userId", source = "user.id")
    CartResponse toCartResponse(Cart cart);
}
