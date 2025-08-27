package com.example.product.service.exception;
public class ProductNameInUseException extends ProductServiceException {
    public ProductNameInUseException(String name) {
        super("Another product already uses name: " + name);
    }
}
