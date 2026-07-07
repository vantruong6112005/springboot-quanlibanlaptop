/*
 * @ (#) ProductServiceImpl.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.service.impl;

/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.iuh.dto.request.ProductRequest;
import vantruong.iuh.dto.response.ProductDTO;
import vantruong.iuh.entity.Brand;
import vantruong.iuh.entity.Category;
import vantruong.iuh.entity.Product;
import vantruong.iuh.exception.AppException;
import vantruong.iuh.exception.BrandNotFoundException;
import vantruong.iuh.exception.CategoryNotFoundException;
import vantruong.iuh.exception.ProductNotFoundException;
import vantruong.iuh.mapper.ProductMapper;
import vantruong.iuh.repository.BrandRepository;
import vantruong.iuh.repository.CategoryRepository;
import vantruong.iuh.repository.ProductRepository;
import vantruong.iuh.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException());
        return productMapper.toProductDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new BrandNotFoundException());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException());

        Product product = productMapper.toProduct(request);
        product.setBrand(brand);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException());
 
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new BrandNotFoundException());
 
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException());

        productMapper.updateProduct(product, request);
        product.setBrand(brand);
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }
}
