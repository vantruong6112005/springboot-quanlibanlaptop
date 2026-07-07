package vantruong.iuh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vantruong.iuh.dto.response.ReviewResponse;
import vantruong.iuh.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "productId", source = "product.id")
    ReviewResponse toReviewResponse(Review review);
}
