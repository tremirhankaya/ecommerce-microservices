package com.example.product.service.service;

import com.example.product.service.model.Product;
import com.example.product.service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    @Transactional

    public Product createProduct(Product product) {
        validate(product);

        if (productRepository.existsByName(product.getName())) {
            throw new RuntimeException("Product already exists: " + product.getName());
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional

    public Product updateProduct(Product updatedProduct) {
        Long updatedProductId = updatedProduct.getId();

        if (updatedProductId == null) {
            throw new RuntimeException("Product id is null");
        }
        if (!productRepository.existsById(updatedProductId)) {
            throw new RuntimeException("Product  does not exist with id: " + updatedProduct.getId());
        }
        validate(updatedProduct);
        Product existing = productRepository.findById(updatedProductId).get();
        existing.setPrice(updatedProduct.getPrice());
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setStock(updatedProduct.getStock());
        return productRepository.save(existing);
    }

    @Override
    @Transactional

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product  does not exist with id: " + id);
        }
        productRepository.deleteById(id);


    }

    @Override
    @Transactional

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    private void validate(Product p) {
        if (p.getName() == null || p.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        BigDecimal price = p.getPrice();
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Price must be zero or positive");
        }
        Integer stock = p.getStock();
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Stock must be zero or positive");
        }
    }

}
