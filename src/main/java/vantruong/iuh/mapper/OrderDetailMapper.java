package vantruong.iuh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vantruong.iuh.dto.response.OrderDetailResponse;
import vantruong.iuh.entity.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
