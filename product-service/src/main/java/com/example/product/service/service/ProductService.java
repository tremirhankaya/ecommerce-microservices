package com.example.product.service.service;

import com.example.product.service.entity.Product;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {
    Product findById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id,Product updated);
    void deleteProduct(Long id);
    List<Product> findAll();
    Product decreaseStock(@Param("id") Long id, @Param("qty") int qty);
    Product increaseStock(@Param("id") Long id, @Param("qty") int qty);


}
