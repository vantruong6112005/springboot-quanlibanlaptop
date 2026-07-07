package vantruong.iuh.service;

import vantruong.iuh.dto.request.BrandRequest;
import vantruong.iuh.dto.response.BrandResponse;
import java.util.List;

public interface BrandService {
    BrandResponse createBrand(BrandRequest request);
    BrandResponse updateBrand(Long id, BrandRequest request);
    BrandResponse getBrandById(Long id);
    List<BrandResponse> getAllBrands();
    void deleteBrand(Long id);
}
