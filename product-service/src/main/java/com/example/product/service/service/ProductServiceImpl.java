package com.example.product.service.service;

import com.example.common.dto.ProductRequest;
import com.example.common.dto.ProductResponse;
import com.example.product.service.business.ProductValidator;
import com.example.product.service.entity.Product;
import com.example.product.service.exception.ProductNotFoundException;
import com.example.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator validator;

    @Override
    public ProductResponse findById(Long id) {
        log.debug("Finding product by id={}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        log.debug("Found product id={} name={}", product.getId(), product.getName());
        return mapProductToResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest req) {
        log.debug("Creating product name={}", req.getName());
        validator.validateForCreate(req);

        Product saved = productRepository.save(mapRequestToEntity(req, new Product()));
        log.info("Created product: {} (id={})", saved.getName(), saved.getId());
        return mapProductToResponse(saved);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest req) {
        log.debug("Updating product id={} newName={}", id, req.getName());
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        validator.validateForUpdate(req, existing.getName());

        Product saved = productRepository.save(mapRequestToEntity(req, existing));
        log.info("Updated product: {} (id={})", saved.getName(), saved.getId());
        return mapProductToResponse(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        log.debug("Deleting product id={}", id);
        if (!productRepository.existsById(id)) {
            log.warn("Delete failed: product not found id={}", id);
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        log.info("Deleted product id={}", id);
    }

    @Override
    public List<ProductResponse> findAll() {
        List<ProductResponse> list = productRepository.findAll()
                .stream()
                .map(this::mapProductToResponse)
                .toList();
        log.debug("Listed products count={}", list.size());
        return list;
    }

    // request -> entity
    private Product mapRequestToEntity(ProductRequest req, Product product) {
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        return product;
    }

    // entity -> response
    private ProductResponse mapProductToResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getStock()
        );
    }
}
