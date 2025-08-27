package com.example.product.service.business;

import com.example.common.dto.ProductRequest;
import com.example.product.service.exception.ProductAlreadyExistsException;
import com.example.product.service.exception.ProductNameInUseException;
import com.example.product.service.exception.ProductValidationException;
import com.example.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    public void validateForCreate(ProductRequest req) {
        validateFields(req);
        assertNameUnique(req.getName());
        log.debug("Create validation OK for name={}", req.getName());
    }

    public void validateForUpdate(ProductRequest req, String currentName) {
        validateFields(req);
        assertNameAvailableOnUpdate(currentName, req.getName());
        log.debug("Update validation OK for newName={}", req.getName());
    }

    private void validateFields(ProductRequest p) {
        if (p.getName() == null || p.getName().isBlank()) {
            throw new ProductValidationException("name cannot be blank");
        }

        if (p.getDescription() != null && p.getDescription().length() > 1000) {
            throw new ProductValidationException("description max 1000 chars");
        }

        BigDecimal price = p.getPrice();
        if (price == null) {
            throw new ProductValidationException("price is required");
        }
        if (price.signum() < 0) {
            throw new ProductValidationException("price must be >= 0");
        }

        Integer stock = p.getStock();
        if (stock == null) {
            throw new ProductValidationException("stock is required");
        }
        if (stock < 0) {
            throw new ProductValidationException("stock must be >= 0");
        }
    }

    private void assertNameUnique(String name) {
        if (productRepository.existsByName(name)) {
            throw new ProductAlreadyExistsException(name);
        }
    }

    private void assertNameAvailableOnUpdate(String currentName, String newName) {
        if (!Objects.equals(currentName, newName) && productRepository.existsByName(newName)) {
            throw new ProductNameInUseException(newName);
        }
    }
}
