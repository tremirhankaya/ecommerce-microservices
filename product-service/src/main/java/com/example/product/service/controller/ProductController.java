package com.example.product.service.controller;

import com.example.product.service.dto.ProductRequest;
import com.example.product.service.dto.ProductResponse;
import com.example.product.service.entity.Product;
import com.example.product.service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody @Valid ProductRequest req) {

        return productService.createProduct(req);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(@PathVariable Long id, @RequestBody @Valid ProductRequest req) {

        return  productService.updateProduct(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.findAll().stream().toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {

        return productService.findById(id);
    }

    @PutMapping("/{id}/decrease")
    public ProductResponse decrease(@PathVariable Long id, @RequestParam int qty) {

        return productService.decreaseStock(id, qty);
    }

    @PutMapping("/{id}/increase")
    public ProductResponse increase(@PathVariable Long id, @RequestParam int qty) {
        return productService.increaseStock(id, qty);
    }


}