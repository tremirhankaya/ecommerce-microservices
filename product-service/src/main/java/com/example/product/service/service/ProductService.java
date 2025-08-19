package com.example.product.service.service;

import com.example.product.service.model.Product;

import java.util.List;

public interface ProductService {
    Product findById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id,Product updated);
    void deleteProduct(Long id);
    List<Product> findAll();

}
