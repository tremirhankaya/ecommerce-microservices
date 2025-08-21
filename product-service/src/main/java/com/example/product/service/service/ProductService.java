package com.example.product.service.service;

import com.example.product.service.dto.ProductRequest;
import com.example.product.service.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse findById(Long id);
    ProductResponse createProduct(ProductRequest req);
    ProductResponse updateProduct(Long id, ProductRequest req);
    void deleteProduct(Long id);
    List<ProductResponse> findAll();
    ProductResponse decreaseStock(Long id, int qty);
    ProductResponse increaseStock(Long id, int qty);
}