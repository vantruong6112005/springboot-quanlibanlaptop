package vantruong.iuh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vantruong.iuh.dto.response.OrderResponse;
import vantruong.iuh.entity.Order;

@Mapper(componentModel = "spring", uses = OrderDetailMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponse toOrderResponse(Order order);
}
