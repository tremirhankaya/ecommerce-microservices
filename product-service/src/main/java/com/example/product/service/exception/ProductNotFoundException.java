package com.example.product.service.exception;

public class ProductNotFoundException extends ProductServiceException {
    public ProductNotFoundException(Long productId) {
        super("Product not found with id: " + productId);
    }
}
