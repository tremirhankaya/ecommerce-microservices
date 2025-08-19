package com.example.product.service.service;

import com.example.product.service.model.Product;
import com.example.product.service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    public Product updateProduct(Long id, Product updated) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (updated == null) {
            throw new IllegalArgumentException("updated product cannot be null");
        }

        validate(updated);

        // 2) Kayıt var mı?
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product does not exist with id: " + id));

        // 3) İsim değişiyorsa benzersizlik kontrolü
        if (!existing.getName().equals(updated.getName())
                && productRepository.existsByName(updated.getName())) {
            throw new RuntimeException("Another product already uses name: " + updated.getName());
        }

        // 4) Alanları güncelle ve kaydet
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());

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
