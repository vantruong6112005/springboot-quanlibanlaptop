package vantruong.iuh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vantruong.iuh.dto.request.CategoryRequest;
import vantruong.iuh.dto.response.CategoryResponse;
import vantruong.iuh.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
