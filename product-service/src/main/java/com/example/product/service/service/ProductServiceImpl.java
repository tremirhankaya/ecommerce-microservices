package com.example.product.service.service;

import com.example.product.service.entity.Product;
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
        System.out.println("Created product: " + product.getName()+ " with id:" + product.getId());
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
        System.out.println("Updated product: " + updated.getName()+ " with id:" + updated.getId());


        return productRepository.save(existing);
    }
    @Override
    @Transactional

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product  does not exist with id: " + id);
        }
        System.out.println("Deleted product with id:" + id);
        productRepository.deleteById(id);


    }

    @Override
    @Transactional

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product decreaseStock(Long id, int qty) {
        if(id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if(qty <= 0) {
            throw new IllegalArgumentException("qty cannot be negative");
        }
        int changed=  productRepository.decreaseStock(id, qty);
        if(changed == 0) {
            if(!productRepository.existsById(id)) {
                throw new RuntimeException("Product does not exist with id: " + id);
            }
            throw new RuntimeException("Insufficient stock for product id: " + id);

        }
        Product refreshed= productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product does not exist with id: " + id));
        System.out.println("Stock decreased");
        return refreshed;

    }

    @Override
    @Transactional
    public Product increaseStock(Long id, int qty) {
        if(id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if(qty <= 0) {
            throw new IllegalArgumentException("qty cannot be negative");
        }
        int changed= productRepository.increaseStock(id, qty);
        if(changed == 0) {
            if(!productRepository.existsById(id)) {
                throw new RuntimeException("Product does not exist with id: " + id);
            }
            throw new RuntimeException("Insufficient stock for product id: " + id);

        }
        Product refreshed= productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product does not exist with id: " + id));
        System.out.println("Stock increased");
        return refreshed;
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
