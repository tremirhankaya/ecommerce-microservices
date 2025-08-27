package com.example.product.service.exception;

public class ProductAlreadyExistsException extends ProductServiceException {
    public ProductAlreadyExistsException(String name) {
        super("Product already exists: " + name);
    }
}
