package com.example.product.service.service;

import com.example.product.service.dto.ProductRequest;
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
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public Product createProduct(ProductRequest req) {
        //  mapleme

        validate(req);

        if (productRepository.existsByName(req.getName())) {
            throw new RuntimeException("Product already exists: " + req.getName());
        }

        Product saved = productRepository.save(mapToEntity(req,new  Product()));
        System.out.println("Created product: " + saved.getName() + " with id:" + saved.getId());
        return saved;
    }


    @Override
    public Product updateProduct(Long id, ProductRequest req) {
        validate(req);
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (req == null) {
            throw new IllegalArgumentException("updated product cannot be null");
        }

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product does not exist with id: " + id));

        if (!existing.getName().equals(req.getName())
                && productRepository.existsByName(req.getName())) {
            throw new RuntimeException("Another product already uses name: " + req.getName());
        }



        Product saved = productRepository.save(mapToEntity(req,existing));
        System.out.println("Updated product: " + saved.getName() + " with id: " + saved.getId());
        return saved;
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product  does not exist with id: " + id);
        }
        System.out.println("Deleted product with id:" + id);
        productRepository.deleteById(id);


    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
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

    private void validate(ProductRequest p) {
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

    private Product mapToEntity(ProductRequest req, Product product) {
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        return product;
    }

}
