package vantruong.iuh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vantruong.iuh.dto.request.BrandRequest;
import vantruong.iuh.dto.response.BrandResponse;
import vantruong.iuh.entity.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(BrandRequest request);
    BrandResponse toBrandResponse(Brand brand);
    void updateBrand(@MappingTarget Brand brand, BrandRequest request);
}
