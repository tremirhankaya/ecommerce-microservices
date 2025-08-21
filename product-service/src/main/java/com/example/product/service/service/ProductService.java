package com.example.product.service.service;

import com.example.product.service.dto.ProductRequest;
import com.example.product.service.entity.Product;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {
    Product findById(Long id);
    public Product createProduct(ProductRequest req);
    Product updateProduct(Long id,ProductRequest req);
    void deleteProduct(Long id);
    List<Product> findAll();
    Product decreaseStock(@Param("id") Long id, @Param("qty") int qty);
    Product increaseStock(@Param("id") Long id, @Param("qty") int qty);


}
