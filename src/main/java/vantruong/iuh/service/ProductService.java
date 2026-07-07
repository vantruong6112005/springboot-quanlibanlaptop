package vantruong.iuh.service;

import vantruong.iuh.dto.request.ProductRequest;
import vantruong.iuh.dto.response.ProductDTO;
import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductRequest request);
    ProductDTO updateProduct(Long id, ProductRequest request);
    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllProducts();
    void deleteProduct(Long id);
    List<ProductDTO> searchByName(String keyword);
}
